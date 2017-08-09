package com.ncm.db;
import java.util.List;

import com.ncm.modelos.Product;

public abstract class ProductDAO extends DAO{
		public abstract List<Product> getTabacosById(int[] tids) throws Exception ;
	}
