package com.ncm.modelos;

import java.sql.Date;

public class AccionUsuario {
	private int idAccionUsuario;
	private Date fechaAccionUsuario;
	private int numAccionUsuario;
	private String detalleAccionUsuario;

	public int getIdAccionUsuario() {
		return idAccionUsuario;
	}

	public void setIdAccionUsuario(int idAccionUsuario) {
		this.idAccionUsuario = idAccionUsuario;
	}

	public Date getFechaAccionUsuario() {
		return fechaAccionUsuario;
	}

	public void setFechaAccionUsuario(Date fechaAccionUsuario) {
		this.fechaAccionUsuario = fechaAccionUsuario;
	}

	public int getNumAccionUsuario() {
		return numAccionUsuario;
	}

	public void setNumAccionUsuario(int numAccionUsuario) {
		this.numAccionUsuario = numAccionUsuario;
	}

	public String getDetalleAccionUsuario() {
		return detalleAccionUsuario;
	}

	public void setDetalleAccionUsuario(String detalleAccionUsuario) {
		this.detalleAccionUsuario = detalleAccionUsuario;
	}

	@Override
	public String toString() {
		return "AccionUsuario [idAccionUsuario=" + idAccionUsuario + ", acción=" + numAccionUsuario + ", detalle="
				+ detalleAccionUsuario + "]";
	}
}