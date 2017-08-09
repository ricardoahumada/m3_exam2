package com.ncm.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

import com.ncm.db.ClienteDAO;
import com.ncm.db.DAOFactory;
import com.ncm.db.MaquinaDAO;
import com.ncm.db.UsuarioDAO;
import com.ncm.modelos.Cliente;
import com.ncm.modelos.Maquina;
import com.ncm.modelos.StatusMessage;
import com.ncm.modelos.Usuario;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/json")
public class DehumoREST {
	private static Logger logger = Logger.getLogger("dehumoREST");
	static List<JsonWebKey> jwkList = null;

	static {
		logger.info("Inside static initializer...");
		jwkList = new LinkedList<>();
		// Creating three keys, will use one now, maybe rework this to be more
		// flexible -- if time permits
		for (int kid = 1; kid <= 3; kid++) {
			JsonWebKey jwk = null;
			try {
				jwk = RsaJwkGenerator.generateJwk(2048);
				logger.info("PUBLIC KEY (" + kid + "): " + jwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY));
			} catch (JoseException e) {
				e.printStackTrace();
			}
			jwk.setKeyId(String.valueOf(kid));
			jwkList.add(jwk);
		}

	}

	@Path("test")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTest() {
		String result = "{\"status\":true}";
		return Response.status(201).entity(result).build();
	}

	@Path("/authenticate")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateCredentials(@HeaderParam("username") String username,
			@HeaderParam("password") String password)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("Authenticating User Credentials...");

		if (username == null) {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.PRECONDITION_FAILED.getStatusCode());
			statusMessage.setMessage("Username value is missing!!!");
			return Response.status(Status.PRECONDITION_FAILED.getStatusCode()).entity(statusMessage).build();
		}

		if (password == null) {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.PRECONDITION_FAILED.getStatusCode());
			statusMessage.setMessage("Password value is missing!!!");
			return Response.status(Status.PRECONDITION_FAILED.getStatusCode()).entity(statusMessage).build();
		}

		Usuario user = null;
		UsuarioDAO usuarioDAO;
		try {
			usuarioDAO = (UsuarioDAO) DAOFactory.getDao("usuario");
			user = usuarioDAO.getUsuario(username, password);
			logger.log(Level.INFO, "user:" + user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (user == null) {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
			statusMessage.setMessage("Access Denied for this functionality !!!");
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();
		}

		RsaJsonWebKey senderJwk = (RsaJsonWebKey) jwkList.get(0);

		senderJwk.setKeyId("1");
		logger.info("JWK (1) ===> " + senderJwk.toJson());

		// Create the Claims, which will be the content of the JWT
		JwtClaims claims = new JwtClaims();
		claims.setIssuer("dehumo.com"); // who creates the token and signs it
		claims.setExpirationTimeMinutesInTheFuture(10); // token will expire (10
														// minutes from now)
		claims.setGeneratedJwtId(); // a unique identifier for the token
		claims.setIssuedAtToNow(); // when the token was issued/created (now)
		claims.setNotBeforeMinutesInThePast(2); // time before which the token
												// is not yet valid (2 minutes
												// ago)
		claims.setSubject(user.getEmail()); // the subject/principal is whom
											// the token is about
		claims.setStringListClaim("roles", "client"); //
		// multi-valued claims for roles
		JsonWebSignature jws = new JsonWebSignature();

		jws.setPayload(claims.toJson());

		jws.setKeyIdHeaderValue(senderJwk.getKeyId());
		jws.setKey(senderJwk.getPrivateKey());

		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

		String jwt = null;
		try {
			jwt = jws.getCompactSerialization();
		} catch (JoseException e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(jwt).build();
	}

	@GET
	@Path("/dashboard/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getDashboard(@PathParam("uid") int uid) {
		logger.log(Level.INFO, "Uid:" + uid);

		Map<String, Object> dashboardMap = new HashMap<String, Object>();

		MaquinaDAO maquinaDAO;
		ClienteDAO clienteDAO;

		List<Maquina> listaMaquinas = new ArrayList<Maquina>();

		try {
			maquinaDAO = (MaquinaDAO) DAOFactory.getDao("maquina");
			listaMaquinas = (List<Maquina>) maquinaDAO.getAllMaquinasForUser(uid);
			logger.log(Level.INFO, "listaMaquinas:" + listaMaquinas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Cliente> listaClientes = new ArrayList<Cliente>();
		try {
			clienteDAO = (ClienteDAO) DAOFactory.getDao("cliente");
			listaClientes = (List<Cliente>) clienteDAO.getClientesForUser(uid);// new
																				// ArrayList<Cliente>();//
			logger.log(Level.INFO, "listaClientes:" + listaClientes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		dashboardMap.put("maquinas", listaMaquinas);
		dashboardMap.put("clientes", listaClientes);

		return dashboardMap;
	}

	@GET
	@Path("/privatedashboard")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPrivateDashboard(@HeaderParam("token") String token) {
		logger.log(Level.INFO, "token:" + token);
		String userEmail = "";

		userEmail = this.getUserEmailFromToken(token);

		if (userEmail == null) {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
			statusMessage.setMessage("Access Denied for this functionality !!!");
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();
		}

		Usuario user;
		UsuarioDAO userDAO;
		int uid = 0;

		try {
			userDAO = (UsuarioDAO) DAOFactory.getDao("usuario");
			user = userDAO.getUsuarioByMail(userEmail);
			uid = user.getUid();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> dashboardMap = new HashMap<String, Object>();

		MaquinaDAO maquinaDAO;
		ClienteDAO clienteDAO;

		List<Maquina> listaMaquinas = new ArrayList<Maquina>();

		try {
			maquinaDAO = (MaquinaDAO) DAOFactory.getDao("maquina");
			listaMaquinas = (List<Maquina>) maquinaDAO.getAllMaquinasForUser(uid);
			logger.log(Level.INFO, "listaMaquinas:" + listaMaquinas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Cliente> listaClientes = new ArrayList<Cliente>();
		try {
			clienteDAO = (ClienteDAO) DAOFactory.getDao("cliente");
			listaClientes = (List<Cliente>) clienteDAO.getClientesForUser(uid);// new
																				// ArrayList<Cliente>();//
			logger.log(Level.INFO, "listaClientes:" + listaClientes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		dashboardMap.put("maquinas", listaMaquinas);
		dashboardMap.put("clientes", listaClientes);

		return Response.status(200).entity(dashboardMap).build();
	}

	@GET
	@Path("/maquinas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMaquinas(@HeaderParam("token") String token) {

		String userEmail = "";

		userEmail = this.getUserEmailFromToken(token);

		if (userEmail == null) {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
			statusMessage.setMessage("Access Denied for this functionality !!!");
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();
		}

		Usuario user;
		UsuarioDAO userDAO;
		int uid = 0;

		try {
			userDAO = (UsuarioDAO) DAOFactory.getDao("usuario");
			user = userDAO.getUsuarioByMail(userEmail);
			uid = user.getUid();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> maquinasMap = new HashMap<String, Object>();

		MaquinaDAO maquinaDAO;

		List<Maquina> listaMaquinas = new ArrayList<Maquina>();

		try {
			maquinaDAO = (MaquinaDAO) DAOFactory.getDao("maquina");
			listaMaquinas = (List<Maquina>) maquinaDAO.getAllMaquinasForUser(uid);
			logger.log(Level.INFO, "listaMaquinas:" + listaMaquinas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		maquinasMap.put("maquinas", listaMaquinas);

		return Response.status(200).entity(maquinasMap).build();

	}

	@GET
	@Path("/maquina/{mid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMaquina(@HeaderParam("token") String token, @PathParam("mid") int mid) {
		logger.log(Level.INFO, "Mid: " + mid);
		logger.log(Level.INFO, "token: " + token);
		
		String userEmail = "";

		userEmail = this.getUserEmailFromToken(token);
		logger.log(Level.INFO, "UserEmail: " + userEmail);

		if (userEmail == null) {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
			statusMessage.setMessage("Access Denied for this functionality !!!");
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();
		}

		Usuario user;
		UsuarioDAO userDAO;
		int uid = 0;

		try {
			userDAO = (UsuarioDAO) DAOFactory.getDao("usuario");
			user = userDAO.getUsuarioByMail(userEmail);
			uid = user.getUid();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (uid != 0) {
			Map<String, Object> maquinaMap = new HashMap<String, Object>();

			MaquinaDAO maquinaDAO;

			Maquina listaMaquina = new Maquina();

			try {
				maquinaDAO = (MaquinaDAO) DAOFactory.getDao("maquina");
				listaMaquina = (Maquina) maquinaDAO.getMaquina(mid);
				logger.log(Level.INFO, "ListaMaquina:" + listaMaquina);
			} catch (Exception e) {
				e.printStackTrace();
			}

			maquinaMap.put("maquinaDetail", listaMaquina);

			logger.log(Level.INFO, "ListaMaquina: " + listaMaquina);

			return Response.status(200).entity(maquinaMap).build();
		} else {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
			statusMessage.setMessage("Usuario no permitido !!!");
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();

		}
	}
//________________
	
	@GET
	@Path("/clientes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClientes(@HeaderParam("token") String token) {

		String userEmail = "";

		userEmail = this.getUserEmailFromToken(token);

		if (userEmail == null) {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
			statusMessage.setMessage("Access Denied for this functionality !!!");
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();
		}

		Usuario user;
		UsuarioDAO userDAO;
		int uid = 0;

		try {
			userDAO = (UsuarioDAO) DAOFactory.getDao("usuario");
			user = userDAO.getUsuarioByMail(userEmail);
			uid = user.getUid();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> clientesMap = new HashMap<String, Object>();

		ClienteDAO clienteDAO;

		List<Cliente> listaClientes = new ArrayList<Cliente>();

		try {
			clienteDAO = (ClienteDAO) DAOFactory.getDao("cliente");
			listaClientes= (List<Cliente>) clienteDAO.getClientesForUser(uid);
			logger.log(Level.INFO, "listaMaquinas:" + listaClientes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		clientesMap.put("clientes", listaClientes);

		return Response.status(200).entity(clientesMap).build();

	}

	@GET
	@Path("/cliente/{cid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCliente(@HeaderParam("token") String token, @PathParam("cid") int cid) {
		logger.log(Level.INFO, "Cid: " + cid);
		logger.log(Level.INFO, "token: " + token);
		
		String userEmail = "";

		userEmail = this.getUserEmailFromToken(token);
		logger.log(Level.INFO, "UserEmail: " + userEmail);

		if (userEmail == null) {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
			statusMessage.setMessage("Access Denied for this functionality !!!");
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();
		}

		Usuario user;
		UsuarioDAO userDAO;
		int uid = 0;

		try {
			userDAO = (UsuarioDAO) DAOFactory.getDao("usuario");
			user = userDAO.getUsuarioByMail(userEmail);
			uid = user.getUid();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (uid != 0) {
			Map<String, Object> clienteMap = new HashMap<String, Object>();

			ClienteDAO clienteDAO;

			Cliente listadoCliente = new Cliente();

			try {
				clienteDAO = (ClienteDAO) DAOFactory.getDao("cliente");
				listadoCliente = (Cliente) clienteDAO.getCliente(cid);
				logger.log(Level.INFO, "ListadoCliente:" + listadoCliente);
			} catch (Exception e) {
				e.printStackTrace();
			}

			clienteMap.put("clienteDetail", listadoCliente);

			logger.log(Level.INFO, "clienteDetail: " + listadoCliente);

			return Response.status(200).entity(clienteMap).build();
		} else {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
			statusMessage.setMessage("Usuario no permitido !!!");
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();

		}
	}
	
	private String getUserEmailFromToken(String token) {
		if (token == null)
			return null;

		String userEmail = null;

		try {

			JsonWebKeySet jwks = new JsonWebKeySet(jwkList);
			JsonWebKey jwk = jwks.findJsonWebKey("1", null, null, null);
			logger.log(Level.INFO, "JWK (1) ===> " + jwk.toJson());

			// Validate Token's authenticity and check claims
			JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime()
					.setAllowedClockSkewInSeconds(30).setRequireSubject().setExpectedIssuer("dehumo.com")
					.setVerificationKey(jwk.getKey()).build();

			// Validate the JWT and process it to the Claims
			JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
			logger.log(Level.INFO, "JWT validation succeeded! " + jwtClaims.getSubject().toString());
			userEmail = jwtClaims.getSubject().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userEmail;
	}

}
