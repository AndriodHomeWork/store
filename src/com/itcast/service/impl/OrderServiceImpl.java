package com.itcast.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.itcast.bean.Order;
import com.itcast.bean.OrderItem;
import com.itcast.bean.PageBean;
import com.itcast.constant.Constant;
import com.itcast.dao.OrderDao;
import com.itcast.dao.impl.OrderDaoImpl;
import com.itcast.service.OrderService;
import com.itcast.utils.C3P0Utils;
import com.itcast.utils.ConnectionManager;


public class OrderServiceImpl implements OrderService {

	@Override
	public void save(Order order) throws Exception  {
		
		
		
		Connection connection = null;
		try {
			//进行事务管理
			 connection = ConnectionManager.getConnectionByLocalThread();
			//开启事务
			connection.setAutoCommit(false);
			
			//1存到订单中
			OrderDao dao = new OrderDaoImpl();
			dao.saveOrder(order);
			//2存到订单项中(order中又购物项集合了)
			List<OrderItem> orderItems = order.getOrderItems();
			for (OrderItem orderItem : orderItems) {
				dao.saveOrderItem(orderItem);
			}
			
			connection.commit();
		} catch (Exception e) {
				e.printStackTrace();
				//回滚事务
				connection.rollback();
				
		}
		
		
		
	}


	@Override
	public PageBean<Order> findBypage(int curPage,String uid) throws Exception {
		//封装pageBean<Order>对象
		
		//1当前页面
		//2每页显示的数量
		int curSize = Constant.ORDER_PAGE_SIZE;
		
		//3总Order数量(Order中包含购物项集合)
		OrderDao dao = new OrderDaoImpl();
		int count= dao.selectCount(uid);
		
		//4总页数
		int totalPage =0;
		if(count % curSize != 0){
			totalPage = count/curSize+1;
		}else{
			totalPage = count/curSize;
		}
		
		//5list<Order>  //TODO
		int b =curSize;
		int a =(curPage-1)*b;
				
		List<Order> list = dao.selectLimit(uid,a,b);
		System.out.println(list);
		
		PageBean<Order> pageBean = new PageBean<Order>();
		pageBean.setCurPage(curPage);
		pageBean.setPageSize(curSize);
		pageBean.setTotalPage(totalPage);
		pageBean.setCount(count);
		pageBean.setList(list);
		
		
		return pageBean;
	}


	@Override
	public Order findByOid(String oid) throws Exception {
		OrderDao dao= new OrderDaoImpl();
		Order order = dao.findByOid(oid);
		return order;
	}


	@Override//TODO
	public void updateOrder(Order order) throws Exception {
		OrderDao dao= new OrderDaoImpl();
		dao.updateOrder(order);
		
	}


	/* 根据状态查找order
	 * @see com.itcast.service.OrderService#findByState(java.lang.String)
	 */
	@Override
	public List<Order> findByState(String state) throws Exception {
		OrderDao dao = new OrderDaoImpl();
		List<Order> list = dao.findByState(state);
		return list;
	}


	


	

}
