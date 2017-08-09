package com.ncm.modelos;

public class Accion {
	private int idAccion;
	private String codigoAccion;
	private String detalleAccion;

	public int getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(int idAccion) {
		this.idAccion = idAccion;
	}

	public String getCodigoAccion() {
		return codigoAccion;
	}

	public void setCodigoAccion(String codigoAccion) {
		this.codigoAccion = codigoAccion;
	}

	public String getDetalleAccion() {
		return detalleAccion;
	}

	public void setDetalleAccion(String detalleAccion) {
		this.detalleAccion = detalleAccion;
	}

	@Override
	public String toString() {
		return "Accion [idAccion=" + idAccion + ", código=" + codigoAccion + ", detalle=" + detalleAccion + "]";
	}
}