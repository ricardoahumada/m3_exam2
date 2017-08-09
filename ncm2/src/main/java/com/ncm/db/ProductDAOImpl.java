package com.ncm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ncm.modelos.Canal;
import com.ncm.modelos.Product;

public class ProductDAOImpl extends ProductDAO{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger("TabacoDAOImpl");
	
	private static ProductDAOImpl instance = null;
	
	public static ProductDAOImpl getInstance() throws Exception {
	      if(instance == null) {
	         instance = new ProductDAOImpl();
	      }
	      return instance;
	}

	@Override
	public List<Product> getTabacosById(int[] tids) throws Exception {
		List<Product> listaDeTabaco = new ArrayList<Product>();
		
		if(tids!=null){
			Connection conn = dataSource.getConnection(); // pool de Tomcat
			
			PreparedStatement pstmt = null;
			String selectSQL = "SELECT * FROM tabaco WHERE tid in(?)";
			pstmt  = conn.prepareStatement(selectSQL);
			pstmt.setString(1, Arrays.toString(tids));
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Product aTabaco= new Product();
				aTabaco.setTid(rs.getInt("thid"));
				aTabaco.setMarca(rs.getString("marca"));
				aTabaco.setModelo(rs.getString("modelo"));
				aTabaco.setNumeroSerie(rs.getString("num_serie"));

				Date tstamp = rs.getTimestamp("modif_date");
				aTabaco.setModifDate(tstamp);

				listaDeTabaco.add(aTabaco);
			}

			pstmt.close();
			conn.close();
		}else listaDeTabaco=null;
		
		return listaDeTabaco;
	}

	
	
	
}