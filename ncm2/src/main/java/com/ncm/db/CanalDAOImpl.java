package com.ncm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ncm.modelos.Canal;
import com.ncm.modelos.Product;

public class CanalDAOImpl extends CanalDAO{
	private static Logger logger = Logger.getLogger("CanalDAOImpl");
	
	private static CanalDAOImpl instance = null;
	
	public static CanalDAOImpl getInstance() throws Exception {
	      if(instance == null) {
	         instance = new CanalDAOImpl();
	      }
	      return instance;
	}

	@Override
	public List<Canal> getCanalesById(int[] chids) throws Exception {
		List<Canal> listaDeCanal = new ArrayList<Canal>();
		logger.log(Level.OFF, "Muestra Canales en CDAO"+ Arrays.toString(chids));
		if(chids!=null){
			Connection conn = dataSource.getConnection(); // pool de Tomcat
			
			PreparedStatement pstmt = null;
			String selectSQL = "SELECT * FROM canal WHERE chid in(?)";
			pstmt  = conn.prepareStatement(selectSQL);
			pstmt.setString(1, Arrays.toString(chids));

			ResultSet rs = pstmt.executeQuery();
			
			List<Product> tabacos=null;
			
			ProductDAO tDAO = (ProductDAO)DAOFactory.getDao("tabaco");
			
			while (rs.next()) {
				Canal aCanal= new Canal();
				aCanal.setChid(rs.getInt("chid"));
				tabacos=tDAO.getTabacosById(new int[]{rs.getInt("tabaco")});
				aCanal.setTabaco(tabacos!=null && tabacos.size()>0?tabacos.get(0):null);
				aCanal.setCarga(rs.getInt("carga"));

				Date tstamp = rs.getTimestamp("modif_date");
				aCanal.setModifDate(tstamp);

				listaDeCanal.add(aCanal);
			}

			pstmt.close();
			conn.close();
		}else listaDeCanal=null;
		
		return listaDeCanal;
	}
}