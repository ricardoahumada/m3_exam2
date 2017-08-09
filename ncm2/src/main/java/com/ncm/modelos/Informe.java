package com.ncm.modelos;

public class Informe {
	private int idInforme;
	private String propietarioInforme;
	private String clienteInforme;
	private Object parametrosInforme;

	public int getIdInforme() {
		return idInforme;
	}

	public void setIdInforme(int idInforme) {
		this.idInforme = idInforme;
	}

	public String getPropietarioInforme() {
		return propietarioInforme;
	}

	public void setPropietarioInforme(String propietarioInforme) {
		this.propietarioInforme = propietarioInforme;
	}

	public String getClienteInforme() {
		return clienteInforme;
	}

	public void setClienteInforme(String clienteInforme) {
		this.clienteInforme = clienteInforme;
	}

	public Object getParametrosInforme() {
		return parametrosInforme;
	}

	public void setParametrosInforme(Object parametrosInforme) {
		this.parametrosInforme = parametrosInforme;
	}

	@Override
	public String toString() {
		return "Informe [idInforme=" + idInforme + ", parámetros=" + parametrosInforme + "]";
	}
}