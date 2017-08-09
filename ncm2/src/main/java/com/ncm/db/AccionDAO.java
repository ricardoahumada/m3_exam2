package com.ncm.db;

import java.util.List;

import com.ncm.modelos.Accion;

public abstract class AccionDAO extends DAO {
	public abstract int creaAccion(Accion accion);

	public abstract Accion muestraAccion(int idAccion);

	public abstract int llenaAccion(int idAccion, Accion accion);

	public abstract Accion actualizaAccion(int idAccion, Accion accion);

	public abstract Accion borraAccion(int idAccion);

	public abstract List<Accion> listarAcciones();
}
