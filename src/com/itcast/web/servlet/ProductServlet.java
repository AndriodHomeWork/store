package com.itcast.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bean.PageBean;
import com.itcast.bean.Product;
import com.itcast.service.ProductService;
import com.itcast.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	
	/**分页查询当前类别下的商品信息
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByPage(HttpServletRequest request,HttpServletResponse response){//TODO
		response.setContentType("text/html,charcater=utf-8");
		  try {
			// 获得请求参数(当前页码, 类别cid)
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			String cid = request.getParameter("cid");
			// 调用业务, 获得分页的数据 PageBean<Product> pageBean
			ProductService service = new ProductServiceImpl();
			PageBean<Product> pageBean = service.findByPage(curPage, cid);
			  
			//把pageBean存到request里面, 转发 "/jsp/product_list.jsp"展示
			request.setAttribute("pageBean", pageBean);
		
			return  "/jsp/product_list.jsp";
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "商品查询失败...");
			return "/jsp/msg.jsp";
		}
		

		
		
		
		
	}
	
	/**商品详情
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByPid(HttpServletRequest request,HttpServletResponse response){
		
		
		try {
			//获得请求参数(pid)
			String pid = request.getParameter("pid");
			//调用业务, 根据pid获得商品数据(Product product)
			ProductService service = new ProductServiceImpl();
			Product product = service.findByPid(pid);
			//把product存到request里面, 转发到 "/jsp/product_info.jsp"页面展示
			request.setAttribute("p", product);
			return "jsp/product_info.jsp";
			
		} catch (Exception e) {
			request.setAttribute("msg", "商品详情获取失败...");
			return "/jsp/msg.jsp";
		}
		
		
		
	
		}
	
	
	
	}


