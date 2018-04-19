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
import com.itcast.utils.UUIDUtils;

public class AdminCategoryServlet extends BaseServlet {
	
	/**添加分类
	 * @param request
	 * @param response
	 * @return
	 */
	public String add(HttpServletRequest request,HttpServletResponse response){
		try {
			//1. 获得请求参数(cname), 封装成category对象
			String cname = request.getParameter("cname");
			Category category = new Category();
			category.setCname(cname);
			category.setCid(UUIDUtils.getId());
			//2. 调用业务(调用保存类别,更新redis里面的数据)
			CategoryService service = new CategoryServiceImpl();
			service.add(category);

			//3. 再查询,展示
			response.sendRedirect(request.getContextPath()+"/adminCategoryServlet?method=findAll");

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**跳转到添加类别的页面
	 * @param request
	 * @param response
	 * @return
	 */
	public String addUI(HttpServletRequest request,HttpServletResponse response){
		
		return "/admin/category/add.jsp";
		
	}
	
	/**后台展示出所有的类别
	 * @param request
	 * @param response
	 * @return
	 */
	public String findAll(HttpServletRequest request,HttpServletResponse response){
		try {
			//调用业务, 获得分类数据 List<Category> list
			CategoryService service = new CategoryServiceImpl();
			
			//把list存到request里面, 转发页面展示
			List<Category> list = service.findlist();
			request.setAttribute("list", list);
			
			
			return "/admin/category/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

}
