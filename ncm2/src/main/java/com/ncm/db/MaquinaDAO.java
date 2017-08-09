package com.ncm.db;
import java.util.List;

import com.ncm.modelos.Maquina;
import com.ncm.modelos.Product;

public abstract class MaquinaDAO extends DAO{
		public abstract List<Maquina> getAllMaquinasForUser(int uid) throws Exception;
		public abstract List<Maquina> getListaMaquinasForClient(int cid) throws Exception;
		public abstract Maquina getMaquina(int mid) throws Exception;		
	}
