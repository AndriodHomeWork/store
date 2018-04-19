package com.itcast.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itcast.bean.Category;
import com.itcast.dao.CategoryDao;
import com.itcast.utils.C3P0Utils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql ="select * from category";
		List<Category> list = runner.query(sql, new BeanListHandler<>(Category.class));
		return list;
	}

	@Override
	public void add(Category category) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql ="insert into category values(?,?)";
		runner.update(sql, category.getCid(),category.getCname());
	}

}
