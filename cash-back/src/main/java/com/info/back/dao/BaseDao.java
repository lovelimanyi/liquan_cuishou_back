package com.info.back.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

public class BaseDao {

	private SqlSessionTemplate sqlSessionTemplate;

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return this.sqlSessionTemplate;
	}

}
