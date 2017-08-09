package com.ncm.modelos;

import java.sql.Date;

public class AccionMaquina {
	private int idAccionMaquina;
	private Date fechaAccionMaquina;
	private int numAccionMaquina;
	private String detalleAccionMaquina;

	public int getIdAccionMaquina() {
		return idAccionMaquina;
	}

	public void setIdAccionMaquina(int idAccionMaquina) {
		this.idAccionMaquina = idAccionMaquina;
	}

	public Date getFechaAccionMaquina() {
		return fechaAccionMaquina;
	}

	public void setFechaAccionMaquina(Date fechaAccionMaquina) {
		this.fechaAccionMaquina = fechaAccionMaquina;
	}

	public int getNumAccionMaquina() {
		return numAccionMaquina;
	}

	public void setNumAccionMaquina(int numAccionMaquina) {
		this.numAccionMaquina = numAccionMaquina;
	}

	public String getDetalleAccionMaquina() {
		return detalleAccionMaquina;
	}

	public void setDetalleAccionMaquina(String detalleAccionMaquina) {
		this.detalleAccionMaquina = detalleAccionMaquina;
	}

	@Override
	public String toString() {
		return "AccionMaquina [idAccionMaquina=" + idAccionMaquina + ", acción=" + numAccionMaquina + ", detalle="
				+ detalleAccionMaquina + "]";
	}
}