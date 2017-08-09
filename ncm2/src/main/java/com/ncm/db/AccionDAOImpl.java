package com.ncm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ncm.modelos.Accion;
import com.ncm.modelos.Product;

public class AccionDAOImpl extends AccionDAO {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger("AccionDAOImpl");
	
	private static AccionDAOImpl instance = null;
	
	public static AccionDAOImpl getInstance() throws Exception {
	      if(instance == null) {
	         instance = new AccionDAOImpl();
	      }
	      return instance;
	}

	
	public int creaAccion(Accion accion) {
		int idDevuelto=0;
//		//Inicialmente, un postulante generará la Accion, pero no habrá ofertas generadas. Por ello, se inicializa a 0.
//		int numeroOfertas=0; 
		try {
			logger.log(Level.OFF,"insertaAccion: Antes getConnection");
			logger.log(Level.OFF,"insertaAccion: Variables: URL -> "+url+" USER -> "+user+" PASSWORD -> "+password );
			//Connection conn = DriverManager.getConnection(url, user, password);
			Connection conn = dataSource.getConnection();  //pool de Tomcat
			
			String sql_insert = "INSERT INTO t_Accion "
					+ "(idAccion, marcaAccion, modeloAccion, numeroSerieAccion, distribuidoresAccion, asignadaAccion)"					
					+ "VALUES (?,?,?,?,?,?)";
			logger.log(Level.OFF,"InsertaAccion: Antes prepareStatement");
			PreparedStatement pstm = conn.prepareStatement(sql_insert);
			
			pstm = conn.prepareStatement(sql_insert,Statement.RETURN_GENERATED_KEYS);
			pstm.setInt(1, Types.INTEGER);
//			pstm.setString(2,Accion.getMarcaAccion());
//			pstm.setString(3,Accion.getModeloAccion());
//			pstm.setString(4,Accion.getNumeroSerieAccion());
//			pstm.setInt(5,Accion.getDistribuidoresAccion());
//			pstm.setInt(6,Accion.getClienteAccion());
			java.sql.Date sqlDate = null;//new java.sql.Date(Accion.getFechaCierre().getTime());
			pstm.setDate(7,sqlDate);
			logger.log(Level.OFF,"InsertaCampaña: Antes executeUpdate");
				
			pstm.executeUpdate();	
			ResultSet rs = pstm.getGeneratedKeys();
			logger.log(Level.OFF,"\nInsertaCampaña: Inserción");
		
			if (rs.next()) {
				idDevuelto=rs.getInt(1);
				if (idDevuelto > 0){
//					System.out.println ("\ninsertaAccion => id: " + idDevuelto + "\n\nDatos Accion insertados: " + Accion.toString());
				}
				else{
					System.out.println("\nNo se ha podido insertar la Accion");
				}
			}	
			
			rs.close();	
			pstm.close();
			conn.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("\nNo se ha podido insertar la Accion");
			e.printStackTrace();
		}					
		
		return idDevuelto;
	}



	@Override
	public Accion borraAccion(int idAccion) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Accion> listarAccion() {
		int idDevuelto = 0;
		List<Accion> listaDeAccion = new ArrayList<Accion>();
		
		try {
			logger.log(Level.OFF,"ListaCampaña: Antes getConnection");
			logger.log(Level.OFF,"ListaCampaña: Variables: URL -> "+url+" USER -> "+user+" PASSWORD -> "+password );
			Connection conn = dataSource.getConnection();  //pool de Tomcat
			
			Statement stmt = conn.createStatement();

//			logger.log(Level.OFF,"Accion.getpostulante: " + Accion.getPostulante());	
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM t_Accion");			

			while (rs.next()) {
				idDevuelto=rs.getInt(1);
				
				Accion aAccion = new Accion();
				aAccion.setIdAccion(rs.getInt("idAccion"));
//				aAccion.setMarcaAccion(rs.getString("marcaAccion"));
//				aAccion.setModeloAccion(rs.getString("modeloAccion"));
//				aAccion.setNumeroSerieAccion(rs.getString("numeroSerieAccion"));
//				aAccion.setDistribuidoresAccion(rs.getInt("distribuidoresAccion"));
//				aAccion.setClienteAccion(rs.getInt("asignadaAccion"));				
				
				listaDeAccion.add(aAccion);
								
				
//				if (idDevuelto > 0){
//					System.out.println ("\nListaCampaña => id: " + idDevuelto + "\n\nDatos Accion devueltos: " + aAccion.getTitulo()+ " " + aAccion.getId());
//				}
//				else{
//					System.out.println("\nNo se ha podido extraer la Accion");
//				}
			}	

			stmt.close();
			conn.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("\nNo se ha podido extraer la Accion");
			e.printStackTrace();
		}					
		
		return listaDeAccion;
	}
	
	public Accion muestraAccion(int idAccion){
		
		Accion aAccion=null;
		try {
			logger.log(Level.OFF,"DetalleAccion: Antes getConnection");
			logger.log(Level.OFF,"DetalleAccion: Variables: URL -> "+url+" USER -> "+user+" PASSWORD -> "+password );
			//Connection conn = DriverManager.getConnection(url, user, password);
			Connection conn = dataSource.getConnection();  //pool de Tomcat
			
			String sql_detalla = "SELECT * from t_Accion where idAccion=?";
					
			logger.log(Level.OFF,"DetalleAccion: Antes prepareStatement");
			PreparedStatement pstm = conn.prepareStatement(sql_detalla);
			
			pstm = conn.prepareStatement(sql_detalla);
			pstm.setInt(1,idAccion);
			
			
			logger.log(Level.OFF,"DetalleAccion: Antes executeUpdate");
			ResultSet rs = pstm.executeQuery();
			
			logger.log(Level.OFF,"\nDetalleAccion: detalles");
			
			aAccion = new Accion();
			
			if (rs.next()) {

				aAccion.setIdAccion(rs.getInt("idAccion"));
//				aAccion.setMarcaAccion(rs.getString("marcaAccion"));
//				aAccion.setModeloAccion(rs.getString("modeloAccion"));
//				aAccion.setNumeroSerieAccion(rs.getString("numeroSerieAccion"));
//				aAccion.setDistribuidoresAccion(rs.getInt("distribuidoresAccion"));
//				aAccion.setClienteAccion(rs.getInt("asignadaAccion"));						
				
				System.out.println ("\nDetalleMáquina => id: " + idAccion + "\n\nDatos máquina detalles: " + aAccion.toString());
				
			}	
			
			rs.close();	
			pstm.close();
			conn.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("\nNo se ha podido mostrar el detalle de la máquina");
			e.printStackTrace();
		}					
		return aAccion;
	}


	/* (non-Javadoc)
	 * @see com.estanco.DAO.AccionDAO#llenaAccion(int, com.estanco.modelos.Accion)
	 */
	@Override
	public int llenaAccion(int idAccion, Accion accion) {
		// TODO Auto-generated method stub
		return 0;
	}


	/* (non-Javadoc)
	 * @see com.estanco.DAO.AccionDAO#actualizaAccion(int, com.estanco.modelos.Accion)
	 */
	@Override
	public Accion actualizaAccion(int idAccion, Accion accion) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.estanco.DAO.AccionDAO#listarAcciones()
	 */
	@Override
	public List<Accion> listarAcciones() {
		// TODO Auto-generated method stub
		return null;
	}
}