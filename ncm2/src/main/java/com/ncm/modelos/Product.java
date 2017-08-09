package com.ncm.modelos;

import java.util.Date;

public class Product {
	private int tid;
	private String marca;
	private String modelo;
	private String numeroSerie;
	private float precio;
	private Date modifDate;

	
	public int getTid() {
		return tid;
	}


	public void setTid(int tid) {
		this.tid = tid;
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


	public float getPrecio() {
		return precio;
	}


	public void setPrecio(float precio) {
		this.precio = precio;
	}


	public Date getModifDate() {
		return modifDate;
	}


	public void setModifDate(Date modifDate) {
		this.modifDate = modifDate;
	}


	@Override
	public String toString() {
		return "Tabaco [idTabaco=" + tid + ", marca=" + marca+", precio=" + precio+ "]";
	}
}