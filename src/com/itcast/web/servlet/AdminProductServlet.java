package com.itcast.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bean.Category;
import com.itcast.bean.Product;
import com.itcast.service.CategoryService;
import com.itcast.service.ProductService;
import com.itcast.service.impl.CategoryServiceImpl;
import com.itcast.service.impl.ProductServiceImpl;


public class AdminProductServlet extends BaseServlet {
	
	/**获得所有类别,跳转页面
	 * @param request
	 * @param response
	 * @return
	 */
	public String addUI(HttpServletRequest request,HttpServletResponse response){
		try {
			//调用业务, 获得列别的数据(List<Category> list)
			CategoryService service = new CategoryServiceImpl();
			List<Category> list = service.findlist();
			//把list存到request里面, 转发到添加商品页面
			request.setAttribute("list", list);


			return "/admin/product/add.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	/**后台展示所有的商品
	 * @param request
	 * @param response
	 * @return
	 */
	public String findAll(HttpServletRequest request,HttpServletResponse response){
		try {
			//调用业务, 获得所有的商品(List<Product> list)
			ProductService service = new ProductServiceImpl();
			List<Product> list = service.findAll();
			//把list存到request里面, 转发页面展示
			request.setAttribute("list", list);


			return "/admin/product/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
