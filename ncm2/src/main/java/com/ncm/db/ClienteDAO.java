package com.ncm.db;

import java.sql.SQLException;
import java.util.List;

import com.ncm.modelos.Cliente;
import com.ncm.modelos.Maquina;

public abstract class ClienteDAO extends DAO {
	public abstract List<Cliente> getClientesForUser(int uid) throws Exception;
	public abstract boolean createClienteForUser(Cliente cliente, int uid) throws Exception;
	public abstract Cliente getCliente(int cid) throws Exception;
	public abstract boolean updateClienteForUser(Cliente cliente, int uid) throws Exception;
	public abstract boolean deleteClienteForUser(int cid, int uid) throws Exception;
}
