package com.itcast.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import org.apache.naming.factory.BeanFactory;*/

import com.itcast.bean.Cart;
import com.itcast.bean.CartItem;
import com.itcast.bean.Order;
import com.itcast.bean.OrderItem;
import com.itcast.bean.PageBean;
import com.itcast.bean.User;
import com.itcast.constant.Constant;
import com.itcast.service.OrderService;
import com.itcast.service.impl.OrderServiceImpl;
import com.itcast.utils.PaymentUtil;
import com.itcast.utils.UUIDUtils;
import com.mysql.jdbc.Connection;
import com.sun.xml.internal.ws.server.ServiceDefinitionImpl;

/**
 */
public class OrderServlet extends BaseServlet{
	
	public String callback(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");

		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 响应数据有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				System.out.println("111");
				request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
				
			} else if (r9_BType.equals("2")) {
				// 服务器点对点 --- 支付公司通知你
				System.out.println("付款成功！222");
				// 修改订单状态 为已付款
				// 回复支付公司
				response.getWriter().print("success");
			}
			
			//修改订单状态
			OrderService orderService = new OrderServiceImpl();
			Order order = orderService.findByOid(r6_Order);
			order.setState(Constant.PAYED);
			orderService.updateOrder(order);
		
			
		} else {
			// 数据无效
			System.out.println("数据被篡改！");
		}
		
		
		return "/jsp/msg.jsp";
		
	}
	
	
	public String pay(HttpServletRequest request,HttpServletResponse response){
		 try {
			//1. 获得请求参数(oid, 地址, 收货人,电话, 银行编号)
				String address = request.getParameter("address");
				String name = request.getParameter("name");
				String telephone = request.getParameter("telephone");
				String oid = request.getParameter("oid");
				
			//2.  调用业务.根据oid查询订单 Order order, 给order设置地址, 收货人,电话
			 OrderService service = new OrderServiceImpl();
				 Order order = service.findByOid(oid);
				 order.setAddress(address);
				 order.setName(name);
				 order.setTelephone(telephone);
				
			 //3更新order
			 service.updateOrder(order);

				 
			// 组织发送支付公司需要哪些数据
				String pd_FrpId = request.getParameter("pd_FrpId");
				String p0_Cmd = "Buy";
				String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
				String p2_Order = oid;
				String p3_Amt = "0.01";
				String p4_Cur = "CNY";
				String p5_Pid = "";
				String p6_Pcat = "";
				String p7_Pdesc = "";
				// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
				// 第三方支付可以访问网址
				String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
				String p9_SAF = "";
				String pa_MP = "";
				String pr_NeedResponse = "1";
				// 加密hmac 需要密钥
				String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
				String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
						p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

				// 发送给第三方
				StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
				sb.append("p0_Cmd=").append(p0_Cmd).append("&");
				sb.append("p1_MerId=").append(p1_MerId).append("&");
				sb.append("p2_Order=").append(p2_Order).append("&");
				sb.append("p3_Amt=").append(p3_Amt).append("&");
				sb.append("p4_Cur=").append(p4_Cur).append("&");
				sb.append("p5_Pid=").append(p5_Pid).append("&");
				sb.append("p6_Pcat=").append(p6_Pcat).append("&");
				sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
				sb.append("p8_Url=").append(p8_Url).append("&");
				sb.append("p9_SAF=").append(p9_SAF).append("&");
				sb.append("pa_MP=").append(pa_MP).append("&");
				sb.append("pd_FrpId=").append(pd_FrpId).append("&");
				sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
				sb.append("hmac=").append(hmac);

				response.sendRedirect(sb.toString());

				return null;



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	public String findByOid(HttpServletRequest request,HttpServletResponse response){
		try {
			//获得请求参数(oid)
			String oid = request.getParameter("oid");
			//调用业务, 根据oid获得Order对象
			OrderService service = new OrderServiceImpl();
			Order order = service.findByOid(oid);
			
			//把order存到域里面.转发到"/jsp/order_info.jsp"展示
			request.setAttribute("o", order);
			return "/jsp/order_info.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "根据订单编号oid付款 错误");
			return "jsp/msg.jsp";
		}
		
		
	}
	
	public String findByPage(HttpServletRequest request,HttpServletResponse response){
		try {
			//0 从session里面获得user, 判断用户是否登录
			User user = (User) request.getSession().getAttribute("user");
			
			if(user == null){
				response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
				return null;
			}
			//1 获取请求参数
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			
			//2调用业务, 分页查询我的订单. PageBean<Order> pageBean
				OrderService service = new OrderServiceImpl();
				PageBean<Order> pageBean = service.findBypage(curPage,user.getUid());
			//3把pageBean存到request里面,转发到 "/jsp/order_list.jsp"展示 TODO
				request.setAttribute("pageBean", pageBean);
				
				
				
				return "jsp/order_list.jsp";
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "分页获取 当前用户订单 失败");
			return "jsp/msg.jsp";
		}
		
	}
	
	/**生成订单,插入到orders 和 orderItem表中
	 * @param request
	 * @param response
	 * @return
	 */
	public String save(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			//0 判断用户是否登录过(判断session里面是否有user)
			User user = (User) request.getSession().getAttribute("user");
			if(user == null){
				//重定向登录页面
				response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
				return null;
			}else {
				
			//1.获得购物车
			Cart cart = (Cart)request.getSession().getAttribute("cart");
				// 2 封装order对象
				Order order = new Order();
				order.setOid(UUIDUtils.getId());
				order.setOrdertime(new Date());                   //
				order.setState(Constant.UN_PAY);
				order.setTotal(cart.getTotal());
				order.setUid(user.getUid());
				order.setUser(user);
				
				// 封装订单项(购物车的购物项 集合 里面)
				List<OrderItem> orderItems = new ArrayList<OrderItem>();
				
				//购物项集合
				Collection<CartItem> values = cart.getValues();//------------------------------------
				
				
				
				for (CartItem cartItem : values) {
					//每一个购物项生成一个订单项
					OrderItem orderItem = new OrderItem();
					//a 封装订单项id
					orderItem.setItemid(UUIDUtils.getId());
					//b  封装购买数量(购物项里面)
					orderItem.setCount(cartItem.getCount());
					//c  封装当前的订单项属于哪一个订单
					orderItem.setOrder(order);
					//d  封装买了什么商品(购物项里面)
					orderItem.setProduct(cartItem.getProduct());
					//e 封装小计(购物项里面)
					orderItem.setSubTotal(cartItem.getSubTotal());
					
					orderItems.add(orderItem);
				}
				order.setOrderItems(orderItems);
	
				//调用业务
				 OrderService service = new OrderServiceImpl();
				 service.save(order);
				 
				 //把order存到session中
				 request.getSession().setAttribute("o", order);
				 response.sendRedirect(request.getContextPath()+"/jsp/order_info.jsp");
			 return null;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		

		
		
	}
	
}
