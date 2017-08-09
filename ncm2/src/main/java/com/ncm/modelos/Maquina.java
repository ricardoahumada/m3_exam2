package com.ncm.modelos;

import java.util.Date;
import java.util.List;

public class Maquina {
	private int mid;
	private int cliente;
	private int propietario;
	private String marca;
	private String modelo;
	private String numeroSerie;
	private List<Canal> canales;
	private int estado;
	private Date modifDate;
	
	
	public int getMid() {
		return mid;
	}


	public void setMid(int mid) {
		this.mid = mid;
	}


	public int getCliente() {
		return cliente;
	}


	public void setCliente(int cliente) {
		this.cliente = cliente;
	}


	public int getPropietario() {
		return propietario;
	}


	public void setPropietario(int propietario) {
		this.propietario = propietario;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public String getNumeroSerie() {
		return numeroSerie;
	}


	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}


	public List<Canal> getCanales() {
		return canales;
	}


	public void setCanales(List<Canal> canales) {
		this.canales = canales;
	}


	public int getEstado() {
		return estado;
	}


	public void setEstado(int estado) {
		this.estado = estado;
	}


	public Date getModifDate() {
		return modifDate;
	}


	public void setModifDate(Date modifDate) {
		this.modifDate = modifDate;
	}


	@Override
	public String toString() {
		return "Maquina: [id=" + mid + ", marca=" + marca+ ", modelo=" + modelo+", canales=" + canales+ "]";
	}
}