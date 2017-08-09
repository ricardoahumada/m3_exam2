package com.nmc.db;

import java.util.logging.Logger;

import org.junit.BeforeClass;

public class TestMaquinaDAO extends TestNCM {
	private static Logger logger = Logger.getLogger("TestMaquinaDAO");

	@BeforeClass
	public static void setUpClass() {
		setUpContext();
	}

}