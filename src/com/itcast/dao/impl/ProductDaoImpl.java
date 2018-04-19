package com.itcast.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itcast.bean.Product;
import com.itcast.constant.Constant;
import com.itcast.dao.ProductDao;
import com.itcast.utils.C3P0Utils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findNews() throws Exception {
		//根据时间排序,  limit 0,9
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product order by pdate desc limit ?,?";
		List<Product> newList = runner.query(sql,new BeanListHandler<>(Product.class), 0,9);
		return newList;
	}

	@Override
	public List<Product> findHot() throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql ="select * from product where is_hot=? limit ?,?";
		List<Product> hotList = runner.query(sql,  new BeanListHandler<>(Product.class),Constant.PRODUCT_IS_HOT,0,9);
		return hotList;
	}

	@Override
	public Product findByPid(String pid) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql ="select * from product where pid =?";
		Product product = runner.query(sql, pid, new BeanHandler<>(Product.class));
		return product;
	}

	@Override
	public int selectCout(String cid) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql ="select count(*) from product where cid = ? ";
		Long count = (Long) runner.query(sql, cid, new ScalarHandler());
		return count.intValue();
	}

	@Override
	public List<Product> selectLimit(String cid, int a, int b) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product where cid = ? limit ?,?";
		return runner.query(sql, new BeanListHandler<>(Product.class),cid,a,b);
	}

	@Override
	public List<Product> findAll() throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql ="select * from product ";
		List<Product> list = runner.query(sql, new BeanListHandler<>(Product.class));
		return list;
	}

	@Override
	public void save(Product product) throws Exception {
		QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params={product.getPid(),product.getPname(),
				product.getMarket_price(),product.getShop_price(),
				product.getPimage(),product.getPdate(),
				product.getIs_hot(),product.getPdesc(),product.getPflag(),
				product.getCategory().getCid()
				
		};
		queryRunner.update(sql,params);
	}

}
