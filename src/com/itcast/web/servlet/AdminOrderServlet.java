package com.itcast.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bean.Order;
import com.itcast.bean.OrderItem;
import com.itcast.constant.Constant;
import com.itcast.service.OrderService;
import com.itcast.service.impl.OrderServiceImpl;
import com.itcast.utils.JsonUtil;

import net.sf.json.util.JSONUtils;

public class AdminOrderServlet extends BaseServlet {
	
	/**更新订单状态
	 * @param request
	 * @param response
	 * @return
	 */
	public String updateState(HttpServletRequest request,HttpServletResponse response){
		try {
			//获得请求参数(oid), 
			String oid = request.getParameter("oid");

			//根据oid获得Order对象,把Order的状态设置为2
			 OrderService service = new OrderServiceImpl();
			 Order order = service.findByOid(oid);
			 order.setState(Constant.DELIVERED);
			 
			//调用业务层, 更新订单
			 service.updateOrder(order);
			 
			// 再查询已经发货的订单展示页面
			 response.sendRedirect(request.getContextPath()+"/adminOrderServlet?method=findByState&state=2");
			 return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			 return null;
		}
		
	}
	
	/**订单详情
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByOid(HttpServletRequest request,HttpServletResponse response){
		try {
			//获取请求参数
			 String oid = request.getParameter("oid");
			//调用业务, 根据oid获得order对象
			 OrderService service = new OrderServiceImpl();
			 Order order = service.findByOid(oid);
			//得到当前订单的订单项, 把订单项集合转成json响应
			 List<OrderItem> orderItems = order.getOrderItems();
			 
			 String list2json = JsonUtil.list2json(orderItems);
			 response.getWriter().print(list2json);
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**根据状态查询相应状态的订单
	 * @return
	 */
	public String findByState(HttpServletRequest request,HttpServletResponse response){
		try {
			//获取请求参数
			String state = request.getParameter("state");
			//调用service
			OrderService service= new OrderServiceImpl();
			List<Order> list = service.findByState(state);
			//存到域对象,请求转发//TODO
			request.setAttribute("list", list);
			
			return "/admin//order/list.jsp";
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
		
	}

}
