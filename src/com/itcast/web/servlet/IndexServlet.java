package com.itcast.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bean.Product;
import com.itcast.service.ProductService;
import com.itcast.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends BaseServlet {
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	public String index(HttpServletRequest request,HttpServletResponse response){
		
		try {
			//调用业务, 获得最新商品的数据(List<Product> newList) 热门商品(List<Product> hostList))
			ProductService service = new ProductServiceImpl();
			List<Product> newList= service.findNews();
			List<Product> hotList = service.findHot();
			//把两个list存到request里面, 转发到index.jsp展示
			request.setAttribute("newList", newList);
			request.setAttribute("hotList", hotList);
			
			return  "/jsp/index.jsp";
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "服务器异常");
			return "/jsp/msg.jsp";
		}
		

		
	
		
		
	}
	

}
