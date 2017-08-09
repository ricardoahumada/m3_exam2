package com.ncm.db;


import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ncm.util.PropertyValues;

public abstract class DAO {
	
	protected Properties properties = null;
	
	protected static String url;
	protected static String user;
	protected static String password;
	protected static String dbdriver;
	protected static String poolresource;
	protected static DataSource dataSource;
	
	public DAO() {
		
		PropertyValues pv = new PropertyValues();
		try {
			this.properties = pv.getPropValues();
			loadProperties();
			//Class.forName(Dao.dbdriver).newInstance();
			Class.forName(this.properties.getProperty("dbdriver")).newInstance();
			dataSource = this.getPoolResource (this.properties.getProperty("pooldataSource"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Properties getProperties() {
		return this.properties;
	}

	public boolean loadProperties() {

		DAO.url 	 = properties.getProperty("url");
		DAO.user 	 = properties.getProperty("user");
		DAO.password = properties.getProperty("password");
		DAO.dbdriver = properties.getProperty("dbdriver");
		DAO.poolresource = properties.getProperty("pooldataSource");
		
		return true;
	}
	
	private DataSource getPoolResource(String poolresource) throws NamingException{
		// TODO Auto-generated method stub
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		DataSource dataSource = (DataSource) envContext.lookup(poolresource);
		
		return dataSource;
	}

}