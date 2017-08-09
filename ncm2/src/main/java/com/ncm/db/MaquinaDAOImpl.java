package com.ncm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ncm.modelos.Canal;
import com.ncm.modelos.Maquina;
import com.ncm.util.StringUtils;

public class MaquinaDAOImpl extends MaquinaDAO {
	private static Logger logger = Logger.getLogger("MaquinaDAOImpl");

	private static MaquinaDAOImpl instance = null;

	public static MaquinaDAOImpl getInstance() throws Exception {
		if (instance == null) {
			instance = new MaquinaDAOImpl();
		}
		return instance;
	}

	public List<Maquina> getAllMaquinasForUser(int uid) throws Exception {
		List<Maquina> listaDeMaquina = new ArrayList<Maquina>();

		Connection conn = dataSource.getConnection(); // pool de Tomcat

		PreparedStatement pstmt = null;
		String selectSQL = "SELECT * FROM maquina WHERE propietario=?";
		pstmt = conn.prepareStatement(selectSQL);
		pstmt.setInt(1, uid);

		ResultSet rs = pstmt.executeQuery();

		List<Canal> listaCanales = null;
		CanalDAO cDAO = (CanalDAO) DAOFactory.getDao("canal");

		while (rs.next()) {
			Maquina aMaquina = new Maquina();
			aMaquina.setMid(rs.getInt("mid"));
			aMaquina.setCliente(rs.getInt("cliente"));
			aMaquina.setPropietario(rs.getInt("propietario"));
			aMaquina.setMarca(rs.getString("marca"));
			aMaquina.setModelo(rs.getString("modelo"));
			aMaquina.setNumeroSerie(rs.getString("num_serie"));
//			listaCanales = cDAO.getCanalesById(StringUtils.toIntArray(rs.getString("canales")));
			listaCanales = cDAO.getCanalesById(StringUtils.toIntArray(rs.getString("canales")));
			logger.log(Level.OFF, "Muestra Canales Maquina "+rs.getString("canales"));			
			logger.log(Level.OFF, "Muestra Canales Maquina listaCanales"+listaCanales);			

			aMaquina.setCanales(listaCanales);
			aMaquina.setEstado(rs.getInt("estado"));

			Date stamp = rs.getTimestamp("modif_date");
			aMaquina.setModifDate(stamp);

			listaDeMaquina.add(aMaquina);
		}

		pstmt.close();
		conn.close();

		return listaDeMaquina;
	}

	@Override
	public List<Maquina> getListaMaquinasForClient(int cid) throws Exception {
		List<Maquina> listaDeMaquina = new ArrayList<Maquina>();

		Connection conn = dataSource.getConnection(); // pool de Tomcat

		PreparedStatement pstmt = null;
		String selectSQL = "SELECT * FROM maquina WHERE cliente=?";
		pstmt = conn.prepareStatement(selectSQL);
		pstmt.setInt(1, cid);

		ResultSet rs = pstmt.executeQuery();

		List<Canal> listaCanales = null;
		CanalDAO cDAO = (CanalDAO) DAOFactory.getDao("canal");

		while (rs.next()) {
			Maquina aMaquina = new Maquina();
			aMaquina.setMid(rs.getInt("mid"));
			aMaquina.setCliente(rs.getInt("cliente"));
			aMaquina.setPropietario(rs.getInt("propietario"));
			aMaquina.setMarca(rs.getString("marca"));
			aMaquina.setModelo(rs.getString("modelo"));
			aMaquina.setNumeroSerie(rs.getString("num_serie"));

			listaCanales = cDAO.getCanalesById(StringUtils.toIntArray(rs.getString("canales")));
			aMaquina.setCanales(listaCanales);
			aMaquina.setEstado(rs.getInt("estado"));

			Date stamp = rs.getTimestamp("modif_date");
			aMaquina.setModifDate(stamp);

			listaDeMaquina.add(aMaquina);
		}

		pstmt.close();
		conn.close();

		return listaDeMaquina;
	}

	@Override
	public Maquina getMaquina(int mid) throws Exception {

		Maquina aMaquina = new Maquina();
		Connection conn = dataSource.getConnection(); // pool de Tomcat

		PreparedStatement pstmt = null;
		String selectSQL = "SELECT * FROM maquina WHERE mid=?";
		pstmt = conn.prepareStatement(selectSQL);
		pstmt.setInt(1, mid);

		ResultSet rs = pstmt.executeQuery();

		List<Canal> listaCanales = null;
		CanalDAO cDAO = (CanalDAO) DAOFactory.getDao("canal");

		while (rs.next()) {
			aMaquina.setMid(rs.getInt("mid"));
			aMaquina.setCliente(rs.getInt("cliente"));
			aMaquina.setPropietario(rs.getInt("propietario"));
			aMaquina.setMarca(rs.getString("marca"));
			aMaquina.setModelo(rs.getString("modelo"));
			aMaquina.setNumeroSerie(rs.getString("num_serie"));

			listaCanales = cDAO.getCanalesById(StringUtils.toIntArray(rs.getString("canales")));
			aMaquina.setCanales(listaCanales);
			logger.log(Level.OFF, "ListaCanales: "+listaCanales);
			
			aMaquina.setEstado(rs.getInt("estado"));

			Date stamp = rs.getTimestamp("modif_date");
			aMaquina.setModifDate(stamp);

		}

		pstmt.close();
		conn.close();

		return aMaquina;
	}

}