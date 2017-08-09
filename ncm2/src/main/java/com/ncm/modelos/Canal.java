package com.ncm.modelos;

import java.util.Date;

public class Canal {
	private int chid;
	private Product tabaco;
	private int carga;
	private int stockMaximo;
	private Date modifDate;

	public int getChid() {
		return chid;
	}


	public void setChid(int chid) {
		this.chid = chid;
	}


	public Product getTabaco() {
		return tabaco;
	}


	public void setTabaco(Product tabaco) {
		this.tabaco = tabaco;
	}


	public int getCarga() {
		return carga;
	}


	public void setCarga(int carga) {
		this.carga = carga;
	}

	
	public int getStockMaximo() {
		return stockMaximo;
	}


	public void setStockMaximo(int stockMaximo) {
		this.stockMaximo = stockMaximo;
	}


	public Date getModifDate() {
		return modifDate;
	}


	public void setModifDate(Date modifDate) {
		this.modifDate = modifDate;
	}


	@Override
	public String toString() {
		return "Canal [idCanal=" + chid + ", tabaco=" + tabaco.toString() + ", carga=" + carga + ", stockMaximo=" + stockMaximo + "]";
	}
}