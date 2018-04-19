package com.itcast.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itcast.bean.Order;
import com.itcast.bean.OrderItem;
import com.itcast.bean.Product;
import com.itcast.dao.OrderDao;
import com.itcast.utils.C3P0Utils;
import com.itcast.utils.ConnectionManager;

public class OrderDaoImpl implements OrderDao {
	
	//下面两个操作在同一事务中
	@Override
	public void saveOrder(Order order) throws Exception {
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),
				order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()
		};
		
		
		runner.update(ConnectionManager.getConnectionByLocalThread(), sql, params);
		
	}

	@Override
	public void saveOrderItem(OrderItem orderItem) throws Exception {
		QueryRunner runner = new QueryRunner();
		String sql ="insert into orderitem values(?,?,?,?,?)";
		Object[] params={orderItem.getItemid(),orderItem.getCount(),orderItem.getSubTotal(),orderItem.getProduct().getPid(),
				orderItem.getOrder().getOid()};
		runner.update(ConnectionManager.getConnectionByLocalThread(), sql, params);
		
	}

	@Override
	public int selectCount(String uid) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		Long n = (Long) runner.query(sql, new ScalarHandler(),uid);
		return n.intValue();
	}

	@Override
	public List<Order> selectLimit(String uid, int a, int b) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from orders where uid = ? limit ?,?";
		//查询出的list都是order集合(没有订单项)
		List<Order> list = runner.query(sql, new BeanListHandler<>(Order.class), uid,a,b);
		
		for (Order order : list) {
			 sql ="select * from orderitem oi,product p where oi.oid = ? And oi.pid = p.pid";
			 
			 List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), order.getOid());
			 
			
			 
			 //具体封装每一条订单项
			 for (Map<String, Object> map : mapList) {
				OrderItem oi = new OrderItem();
				BeanUtils.populate(oi, map);
				
				Product product = new Product();
				BeanUtils.populate(product, map);
				oi.setProduct(product);//把封装好的商品封装到订单项里面
				//把封装好的订单项 添加到order里面
				order.getOrderItems().add(oi);
			}
		}
		
		return list;
	}

	@Override//TODO
	public Order findByOid(String oid) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql ="select * from orders where oid =?";
		Order order = runner.query(sql, new BeanHandler<>(Order.class), oid);
		sql= "select * from orderitem oi,product p where oi.oid = ? And oi.pid = p.pid";
		List<Map<String, Object>> mapList = runner.query( sql, new MapListHandler(), oid);
		
		for (Map<String, Object> map : mapList) {
			OrderItem oi = new OrderItem();
			BeanUtils.populate(oi, map);
			
			Product product = new Product();
			BeanUtils.populate(product, map);
			oi.setProduct(product);//把封装好的商品封装到订单项里面
			//把封装好的订单项 添加到order里面
			order.getOrderItems().add(oi);
		}
		return order;
	}

	@Override
	public void updateOrder(Order order) throws Exception {//TODO
		QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update orders set state=?,address = ?,name = ?,telephone = ? where oid = ?";
		Object[] params = {order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
		queryRunner.update(sql,params ); 
		
	}

	/* 根据状态查找订单
	 * @see com.itcast.dao.OrderDao#findByState(java.lang.String)
	 */
	@Override
	public List<Order> findByState(String state) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql ="select * from orders ";
		
		if(state != null){
			sql += " where state = ?";
			List<Order> list = runner.query(sql, state, new BeanListHandler<>(Order.class));
			return list;
		}
		
		List<Order> list = runner.query(sql, new BeanListHandler<>(Order.class));
		
		return list;
	}



	

}
