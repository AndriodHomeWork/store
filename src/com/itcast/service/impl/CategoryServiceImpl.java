package com.itcast.service.impl;

import java.util.List;

import com.itcast.bean.Category;
import com.itcast.dao.CategoryDao;
import com.itcast.dao.impl.CategoryDaoImpl;
import com.itcast.service.CategoryService;
import com.itcast.utils.JsonUtil;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public String findAll()  throws Exception{
		
		return findAllFromMysql();
	}
	
	public String findAllFromMysql() throws Exception{
		
		//把list转成JSON(json_lib)
		String data = JsonUtil.list2json(findlist());
		return data;
		
	}
	
	public List<Category> findlist() throws Exception {
		//调用Dao, 查询所有的类别(List<Category> list)
		CategoryDao dao = new CategoryDaoImpl();
		List<Category> list = dao.findAll();
		return list;
		
	}

	@Override
	public void add(Category category) throws Exception {
		CategoryDao dao = new CategoryDaoImpl();
		dao.add(category);
		
	}
	
	

}
