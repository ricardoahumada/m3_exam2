package com.ncm.db;

import java.util.List;

import com.ncm.modelos.Canal;

public abstract class CanalDAO extends DAO {
	public abstract List<Canal> getCanalesById(int[] chids) throws Exception;

}
