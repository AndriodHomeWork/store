package com.itcast.service;

import java.util.List;

import com.itcast.bean.Order;
import com.itcast.bean.PageBean;

public interface OrderService {



	void save(Order order) throws Exception;


	PageBean<Order> findBypage(int curPage, String uid)throws Exception;


	Order findByOid(String oid) throws Exception;


	void updateOrder(Order order)throws Exception;
	
	List<Order> findByState(String state)throws Exception;


	


	



}
