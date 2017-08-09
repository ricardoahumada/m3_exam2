package com.ncm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ncm.modelos.Canal;
import com.ncm.modelos.Cliente;
import com.ncm.modelos.Maquina;
import com.ncm.util.StringUtils;

public class ClienteDAOImpl extends ClienteDAO {
	private static Logger logger = Logger.getLogger("ClienteDAOImpl");

	private static ClienteDAOImpl instance = null;

	public static ClienteDAOImpl getInstance() throws Exception {
		if (instance == null) {
			instance = new ClienteDAOImpl();
		}
		return instance;
	}

	public List<Cliente> getClientesForUser(int uid) throws Exception {
		List<Cliente> listaDeCliente = new ArrayList<Cliente>();

		Connection conn = dataSource.getConnection(); // pool de Tomcat

		PreparedStatement pstmt = null;
		String selectSQL = "SELECT * FROM cliente WHERE propietario=?";
		pstmt = conn.prepareStatement(selectSQL);
		pstmt.setInt(1, uid);

		ResultSet rs = pstmt.executeQuery();

		List<Canal> listaCanales = null;
		CanalDAO cDAO = (CanalDAO) DAOFactory.getDao("canal");

		while (rs.next()) {
			Cliente aCliente = new Cliente();
			aCliente.setCid(rs.getInt("cid"));
			aCliente.setPropietario(rs.getInt("propietario"));
			aCliente.setNombre(rs.getString("nombre"));
			aCliente.setDireccion(rs.getString("direccion"));
			aCliente.setTelefono(rs.getInt("telefono"));
			aCliente.setMovil(rs.getInt("movil"));
			aCliente.setEmail(rs.getString("email"));

			Date stamp = rs.getTimestamp("creationdate");
			aCliente.setFechaCreacion(stamp);

			aCliente.setStatus(rs.getInt("status"));

			aCliente.setEmpresa(rs.getString("empresa"));
			aCliente.setNif(rs.getString("NIF"));
			aCliente.setPais(rs.getString("pais"));

			listaDeCliente.add(aCliente);
		}

		pstmt.close();
		conn.close();

		return listaDeCliente;
	}

	@Override
	public Cliente getCliente(int cid) throws Exception {

		Cliente aCliente = new Cliente();
		Connection conn = dataSource.getConnection(); // pool de Tomcat

		PreparedStatement pstmt = null;
		String selectSQL = "SELECT * FROM cliente WHERE cid=?";
		pstmt = conn.prepareStatement(selectSQL);
		pstmt.setInt(1, cid);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			aCliente.setCid(rs.getInt("cid"));
			aCliente.setNombre(rs.getString("nombre"));
			aCliente.setPropietario(rs.getInt("propietario"));
			aCliente.setTelefono(rs.getInt("telefono"));
			aCliente.setMovil(rs.getInt("movil"));
			aCliente.setEmail(rs.getString("email"));
			aCliente.setDireccion(rs.getString("direccion"));
			aCliente.setEmpresa(rs.getString("empresa"));
			aCliente.setNif(rs.getString("NIF"));
			aCliente.setPais(rs.getString("pais"));
		}

		pstmt.close();
		conn.close();

		return aCliente;
	}

	@Override
	public boolean createClienteForUser(Cliente cliente, int uid) throws Exception {
		boolean idDevuelto = false;
		int valor = 0;
		try {
			Connection conn = dataSource.getConnection(); // pool de Tomcat

			String sql_insert = "INSERT INTO cliente "
					+ "(propietario, nombre, direccion, telefono, movil, email, status, empresa, NIF, pais)"
					+ "VALUES (?,?,?,?,?,?,?,?,?,?)";
			logger.log(Level.OFF, "InsertaCliente: Antes prepareStatement");
			PreparedStatement pstm = conn.prepareStatement(sql_insert);

			pstm = conn.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS);
			pstm.setInt(1, uid);
			pstm.setString(2, cliente.getNombre());
			pstm.setString(3, cliente.getDireccion());
			pstm.setInt(4, cliente.getTelefono());
			pstm.setInt(5, cliente.getMovil());
			pstm.setString(6, cliente.getEmail());
			// java.sql.Date sqlDate = null;//new
			// java.sql.Date(cliente.getFechaCierre().getTime());
			// pstm.setDate(7,sqlDate);
			pstm.setInt(7, cliente.getStatus());
			pstm.setString(8, cliente.getEmpresa());
			pstm.setString(9, cliente.getNif());
			pstm.setString(10, cliente.getPais());

			logger.log(Level.OFF, "InsertaCliente: Antes executeUpdate");

			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			logger.log(Level.OFF, "\nInsertaCliente: Inserción");

			if (rs.next()) {
				valor = rs.getInt(1);
				// idDevuelto=rs.getInt(1);
				if (valor > 0) {
					idDevuelto = true;
					System.out.println(
							"\ninsertaCliente => id: " + valor + "\n\nDatos cliente insertados: " + cliente.toString());
				} else {
					idDevuelto = false;
					System.out.println("\nNo se ha podido insertar el cliente");
				}
			}

			rs.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("\nNo se ha podido insertar el cliente");
			e.printStackTrace();
		}

		return idDevuelto;

	}

	@Override
	public boolean updateClienteForUser(Cliente cliente, int uid) throws Exception {
		int result = 0;
		try {
			Connection conn = dataSource.getConnection();

			String sql = "UPDATE cliente SET nombre = ?, direccion = ?, telefono = ?, movil = ?, email = ?, empresa = ?, NIF= ?, pais= ? WHERE cid="
					+ cliente.getCid();

			PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstm.setString(1, cliente.getNombre());
			pstm.setString(2, cliente.getDireccion());
			pstm.setInt(3, cliente.getTelefono());
			pstm.setInt(4, cliente.getMovil());
			pstm.setString(5, cliente.getEmail());
			pstm.setString(6, cliente.getEmpresa());
			pstm.setString(7, cliente.getNif());
			pstm.setString(8, cliente.getPais());

			result = pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			logger.log(Level.OFF, "\nUpdateClienteForUser: Actualizacion");

			while (rs.next()) {
				result = 1;
			}

			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("\nNo se ha podido actualizar al cliente");
			e.printStackTrace();
		}

		return result == 1;

	}

	@Override
	public boolean deleteClienteForUser(int cid, int uid) throws Exception {

		int result = 0;
		try {
			Connection conn = dataSource.getConnection(); // pool de Tomcat

			String sql = "DELETE FROM cliente WHERE cid=" + cid;

			PreparedStatement pstm = conn.prepareStatement(sql);

			result = pstm.executeUpdate();

			pstm.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("\nNo se ha podido borrar al cliente");
			e.printStackTrace();
		}
		return result == 1;
	}

}