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

import com.ncm.modelos.Informe;

public class InformeDAOImpl extends InformeDAO{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger("InformeDAOImpl");
	
	private static InformeDAOImpl instance = null;
	
	public static InformeDAOImpl getInstance() throws Exception {
	      if(instance == null) {
	         instance = new InformeDAOImpl();
	      }
	      return instance;
	}

	
	public int creaInforme(Informe Informe) {
		int idDevuelto=0;
//		//Inicialmente, un postulante generará la Informe, pero no habrá ofertas generadas. Por ello, se inicializa a 0.
//		int numeroOfertas=0; 
		try {
			logger.log(Level.OFF,"insertaInforme: Antes getConnection");
			logger.log(Level.OFF,"insertaInforme: Variables: URL -> "+url+" USER -> "+user+" PASSWORD -> "+password );
			//Connection conn = DriverManager.getConnection(url, user, password);
			Connection conn = dataSource.getConnection();  //pool de Tomcat
			
			String sql_insert = "INSERT INTO t_Informe "
					+ "(idInforme, marcaInforme, modeloInforme, numeroSerieInforme, distribuidoresInforme, asignadaInforme)"					
					+ "VALUES (?,?,?,?,?,?)";
			logger.log(Level.OFF,"InsertaInforme: Antes prepareStatement");
			PreparedStatement pstm = conn.prepareStatement(sql_insert);
			
			pstm = conn.prepareStatement(sql_insert,Statement.RETURN_GENERATED_KEYS);
			pstm.setInt(1, Types.INTEGER);
//			pstm.setString(2,Informe.getMarcaInforme());
//			pstm.setString(3,Informe.getModeloInforme());
//			pstm.setInt(4,Informe.getNumeroSerieInforme());
//			pstm.setInt(5,Informe.getDistribuidoresInforme());
//			pstm.setInt(6,Informe.getClienteInforme());
			java.sql.Date sqlDate = null;//new java.sql.Date(Informe.getFechaCierre().getTime());
			pstm.setDate(7,sqlDate);
			logger.log(Level.OFF,"InsertaCampaña: Antes executeUpdate");
				
			pstm.executeUpdate();	
			ResultSet rs = pstm.getGeneratedKeys();
			logger.log(Level.OFF,"\nInsertaCampaña: Inserción");
		
			if (rs.next()) {
				idDevuelto=rs.getInt(1);
				if (idDevuelto > 0){
					System.out.println ("\ninsertaInforme => id: " + idDevuelto + "\n\nDatos Informe insertados: " + Informe.toString());
				}
				else{
					System.out.println("\nNo se ha podido insertar la Informe");
				}
			}	
			
			rs.close();	
			pstm.close();
			conn.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("\nNo se ha podido insertar la Informe");
			e.printStackTrace();
		}					
		
		return idDevuelto;
	}


	@Override
	public int llenaInforme(int idInforme, Informe Informe) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Informe actualizaInforme(int idInforme, Informe Informe) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Informe borraInforme(int idInforme) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Informe> listarInformes() {
		int idDevuelto = 0;
		List<Informe> listaDeInforme = new ArrayList<Informe>();
		
		try {
			logger.log(Level.OFF,"ListaCampaña: Antes getConnection");
			logger.log(Level.OFF,"ListaCampaña: Variables: URL -> "+url+" USER -> "+user+" PASSWORD -> "+password );
			Connection conn = dataSource.getConnection();  //pool de Tomcat
			
			Statement stmt = conn.createStatement();

//			logger.log(Level.OFF,"Informe.getpostulante: " + Informe.getPostulante());	
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM t_Informe");			

			while (rs.next()) {
				idDevuelto=rs.getInt(1);
				
				Informe aInforme = new Informe();
				aInforme.setIdInforme(rs.getInt("idInforme"));
//				aInforme.setMarcaInforme(rs.getString("marcaInforme"));
//				aInforme.setModeloInforme(rs.getString("modeloInforme"));
//				aInforme.setNumeroSerieInforme(rs.getString("numeroSerieInforme"));
//				aInforme.setDistribuidoresInforme(rs.getInt("distribuidoresInforme"));
//				aInforme.setClienteInforme(rs.getInt("asignadaInforme"));				
				
				listaDeInforme.add(aInforme);
								
				
//				if (idDevuelto > 0){
//					System.out.println ("\nListaCampaña => id: " + idDevuelto + "\n\nDatos Informe devueltos: " + aInforme.getTitulo()+ " " + aInforme.getId());
//				}
//				else{
//					System.out.println("\nNo se ha podido extraer la Informe");
//				}
			}	

			stmt.close();
			conn.close();			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("\nNo se ha podido extraer la Informe");
			e.printStackTrace();
		}					
		
		return listaDeInforme;
	}
	
	public Informe muestraInforme(int idInforme){
		
		Informe aInforme=null;
		try {
			logger.log(Level.OFF,"DetalleInforme: Antes getConnection");
			logger.log(Level.OFF,"DetalleInforme: Variables: URL -> "+url+" USER -> "+user+" PASSWORD -> "+password );
			//Connection conn = DriverManager.getConnection(url, user, password);
			Connection conn = dataSource.getConnection();  //pool de Tomcat
			
			String sql_detalla = "SELECT * from t_Informe where idInforme=?";
					
			logger.log(Level.OFF,"DetalleInforme: Antes prepareStatement");
			PreparedStatement pstm = conn.prepareStatement(sql_detalla);
			
			pstm = conn.prepareStatement(sql_detalla);
			pstm.setInt(1,idInforme);
			
			
			logger.log(Level.OFF,"DetalleInforme: Antes executeUpdate");
			ResultSet rs = pstm.executeQuery();
			
			logger.log(Level.OFF,"\nDetalleInforme: detalles");
			
			aInforme = new Informe();
			
			if (rs.next()) {

				aInforme.setIdInforme(rs.getInt("idInforme"));
//				aInforme.setMarcaInforme(rs.getString("marcaInforme"));
//				aInforme.setModeloInforme(rs.getString("modeloInforme"));
//				aInforme.setNumeroSerieInforme(rs.getString("numeroSerieInforme"));
//				aInforme.setDistribuidoresInforme(rs.getInt("distribuidoresInforme"));
//				aInforme.setClienteInforme(rs.getInt("asignadaInforme"));						
				
				System.out.println ("\nDetalleMáquina => id: " + idInforme + "\n\nDatos máquina detalles: " + aInforme.toString());
				
			}	
			
			rs.close();	
			pstm.close();
			conn.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("\nNo se ha podido mostrar el detalle de la máquina");
			e.printStackTrace();
		}					
		return aInforme;
	}

	
}