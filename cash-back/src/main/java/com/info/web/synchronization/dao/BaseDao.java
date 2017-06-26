package com.info.web.synchronization.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

public class BaseDao {
	
	private SqlSessionTemplate sqlSessionTemplates;

	@Resource
	public void setSqlSessionTemplates(SqlSessionTemplate sqlSessionTemplates) {
		this.sqlSessionTemplates = sqlSessionTemplates;
	}

	public SqlSessionTemplate getSqlSessionTemplates() {
		return this.sqlSessionTemplates;
	}

}
