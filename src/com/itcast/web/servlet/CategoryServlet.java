package com.itcast.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bean.Category;
import com.itcast.service.CategoryService;
import com.itcast.service.impl.CategoryServiceImpl;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	
	public String findAll(HttpServletRequest request,HttpServletResponse response){
		
	
	
		 try {
			//调用业务, 获得分类数据(json串)
			 CategoryService service = new CategoryServiceImpl();
			 String data = service.findAll();
			//响应给前端 
			 response.getWriter().println(data);
			
			 return null;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

}
