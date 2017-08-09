package com.ncm.db;
import java.util.List;

import com.ncm.modelos.Informe;

public abstract class InformeDAO extends DAO{
		public abstract int creaInforme(Informe Informe);
		public abstract Informe muestraInforme(int idInforme);
		public abstract int llenaInforme(int idInforme, Informe Informe);		
		public abstract Informe actualizaInforme(int idInforme, Informe Informe);
		public abstract Informe borraInforme(int idInforme);
		public abstract List<Informe> listarInformes();
	}
