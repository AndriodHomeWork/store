package com.itcast.service;

import java.util.List;

import com.itcast.bean.Category;

public interface CategoryService {

	String findAll() throws Exception;

	List<Category> findlist()throws Exception;

	void add(Category category)throws Exception;

}
