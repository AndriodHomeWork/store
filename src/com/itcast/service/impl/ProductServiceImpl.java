package com.itcast.service.impl;

import java.util.List;

import com.itcast.bean.PageBean;
import com.itcast.bean.Product;
import com.itcast.constant.Constant;
import com.itcast.dao.ProductDao;
import com.itcast.dao.impl.ProductDaoImpl;
import com.itcast.service.ProductService;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> findNews() throws Exception {
	ProductDao dao = new ProductDaoImpl();
	List<Product> news = dao.findNews();
		return news;
	}

	@Override
	public List<Product> findHot() throws Exception {
		ProductDao dao = new ProductDaoImpl();
		List<Product> hot = dao.findHot();
		
		
		return hot;
	}

	@Override
	public Product findByPid(String pid) throws Exception {
		ProductDao dao = new ProductDaoImpl();
		Product product = dao.findByPid(pid);
		return product;
	}

	@Override
	public PageBean<Product> findByPage(int curPage, String cid) throws Exception {
		//调用Dao, 封装PageBean
	  ProductDao dao = new ProductDaoImpl();
	  
	
	  
	  //1.获得当前类别下的总数量(通过cid查) count---------------
	  int count = dao.selectCout(cid);
	  
	  //2.一页显示的数量 pageSize
	  int pageSize = Constant.PRODUCT_PAGE_SIZE;
	  
	  int totalPage;
	//3.总页数 totalPage
	  if(count % pageSize ==0){
		 totalPage  = count/pageSize;
	  }else{
		   totalPage = count/pageSize+1;
	  }
	  
	  //4当前页curPage
	  
	  //5商品的List,本类-------------------------cid,a,b
	  	int b = pageSize;
		int a = (curPage -1)*b;
		List<Product> list = dao.selectLimit(cid,a,b);
	  
		//1. 封装PageBean
		PageBean pageBean = new PageBean();
		pageBean.setCurPage(curPage);
		pageBean.setCount(count);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public List<Product> findAll() throws Exception {
		  ProductDao dao = new ProductDaoImpl();
		  List<Product> list= dao.findAll();
		return list;
	}

	@Override
	public void add(Product product) throws Exception {
		ProductDao productDao = new ProductDaoImpl();
		productDao.save(product);
		
	}

}
