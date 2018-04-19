package com.itcast.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bean.Cart;
import com.itcast.bean.CartItem;
import com.itcast.bean.Product;
import com.itcast.service.ProductService;
import com.itcast.service.impl.ProductServiceImpl;
public class CartServlet extends BaseServlet {
	
	//将购物项添加到购物车---------商品和数量封装成购物项
	public String addToCart(HttpServletRequest request,HttpServletResponse response){
		try {
			//接收请求参数(pid, count)
			String pid = request.getParameter("pid");
			int count = Integer.parseInt(request.getParameter("count"));
			

			//调用业务根据pid获得Product对象,  把Product和购买数量封装成一个购物项
			 ProductService service = new ProductServiceImpl(); 
			 Product product =service.findByPid(pid);
			 
			 CartItem cartItem = new CartItem();
			 cartItem.setProduct(product);
			 cartItem.setCount(count);
			// 从session里面获得购物车(判断购物车是否存在), 把购物项添加到购物车里面
			 Cart cart = (Cart)request.getSession().getAttribute("cart");
			 if(cart == null){
				 cart = new Cart();
			 }
			 cart.addTOCart(cartItem);
				//3.1 再存到session
				request.getSession().setAttribute("cart", cart);
			 
			// 重定向到购物车页面,展示
				response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
			 return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "添加购物车失败");
			return "/jsp/msg.jsp";
		}

		
	}
	
	//从购物车移除一个购物项
	public String deleteByPid(HttpServletRequest request,HttpServletResponse response){
		try {
			//获得请求参数(pid)
			String pid = request.getParameter("pid");
			
			
			
			//从session里面获得购物车, 调用removeFromCart()
			Cart cart = (Cart)request.getSession().getAttribute("cart");
			cart.removeFromCart(pid);
			//重定向到当前页面展示
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
			return null;
			
		} catch (IOException e) {
			e.printStackTrace();
			request.setAttribute("msg", "删除商品错误");
			return "/jsp/msg.jsp";
		}

	}
	
	public String clearCart(HttpServletRequest request, HttpServletResponse response){
		
		try {
			//从session里面获得购物车, 调用clearCart()
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			cart.clearCatr();
			//重定向cart.jsp页面
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");

			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			request.setAttribute("msg", "删除商品错误");
			return "/jsp/msg.jsp";
		}
		
	}

}
