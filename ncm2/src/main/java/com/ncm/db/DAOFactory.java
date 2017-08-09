package com.ncm.db;

public class DAOFactory {

	public static final DAO getDao(String tipo) throws Exception {
		switch (tipo) {
		case "maquina":
			MaquinaDAO mDao = MaquinaDAOImpl.getInstance();
			return mDao;
		case "canal":
			CanalDAO chDao = CanalDAOImpl.getInstance();
			return chDao;
		case "tabaco":
			ProductDAO tDao = ProductDAOImpl.getInstance();
			return tDao;
		case "cliente":
			ClienteDAO cDao = ClienteDAOImpl.getInstance();
			return cDao;
		case "usuario":
			UsuarioDAO uDao = UsuarioDAOImpl.getInstance();
			return uDao;
		}

		return null;
	}

}