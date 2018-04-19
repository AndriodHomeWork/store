package com.itcast.dao;

import java.util.List;

import com.itcast.bean.Product;

public interface ProductDao {

	List<Product> findNews() throws Exception;

	List<Product> findHot() throws Exception;

	Product findByPid(String pid) throws Exception;

	int selectCout(String cid) throws Exception;

	List<Product> selectLimit(String cid, int a, int b) throws Exception;

	List<Product> findAll()throws Exception;

	void save(Product product)throws Exception;

}
