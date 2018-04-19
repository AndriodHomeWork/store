package com.itcast.dao;

import java.util.List;

import com.itcast.bean.Order;
import com.itcast.bean.OrderItem;

public interface OrderDao {

	void saveOrder(Order order) throws Exception;

	void saveOrderItem(OrderItem orderItem)  throws Exception;

	

	List<Order> selectLimit(String uid, int a, int b) throws Exception;

	int selectCount(String uid)  throws Exception;

	Order findByOid(String oid) throws Exception;

	void updateOrder(Order order) throws Exception;

	List<Order> findByState(String state)throws Exception;


	

}
