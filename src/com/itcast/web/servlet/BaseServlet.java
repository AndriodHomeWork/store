package com.itcast.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class BaseServlet extends HttpServlet {
	public void service(HttpServletRequest request,HttpServletResponse response){
		
		try {
			//0,处理响应的乱码
			response.setContentType("text/html;charset=utf-8");
			
			Class clazz = this.getClass();
			String methodName = request.getParameter("method");
			Method method = clazz.getMethod(methodName,HttpServletRequest.class, HttpServletResponse.class);
			String path = (String)method.invoke(this,request, response);
			
			
			if(path != null){
				request.getRequestDispatcher(path).forward(request, response);
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
