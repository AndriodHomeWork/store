package com.itcast.dao;

import java.util.List;

import com.itcast.bean.Category;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

	void add(Category category) throws Exception;

}
