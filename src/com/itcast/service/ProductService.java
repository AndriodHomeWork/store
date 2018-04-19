package com.itcast.service;

import java.util.List;

import com.itcast.bean.PageBean;
import com.itcast.bean.Product;

public interface ProductService {

	List<Product> findNews() throws Exception;

	List<Product> findHot() throws Exception;

	Product findByPid(String pid) throws Exception;

	PageBean<Product> findByPage(int curPage, String cid) throws Exception;

	List<Product> findAll()throws Exception;

	void add(Product product)throws Exception;

}
