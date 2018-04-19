package com.itcast.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import org.apache.catalina.connector.Request;*/
import org.apache.commons.beanutils.BeanUtils;

import com.itcast.bean.User;
import com.itcast.constant.Constant;
import com.itcast.service.UserService;
import com.itcast.service.impl.UserServiceImpl;
import com.itcast.utils.UUIDUtils;

import cn.dsna.util.images.ValidateCode;

/**
 */
public class UserServlet extends BaseServlet {

	
	//跳转页面
	public String loginUI(HttpServletRequest request,HttpServletResponse response){
		return "/jsp/login.jsp";
		
	}
	public String registerUI(HttpServletRequest request,HttpServletResponse response){
		return "/jsp/register.jsp";
		
	}
	
	/**验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String vcode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//1.创建ValidateCode对象
				ValidateCode validateCode = new ValidateCode(120, 50, 4, 10);
				String vcode = validateCode.getCode();
				
				//将vcode存放到session中
				request.getSession().setAttribute("vcode", vcode);
				
				//2.将验证码图片输出到客户端
				ServletOutputStream outputStream = response.getOutputStream();
				validateCode.write(outputStream);
				return null;
	}
	/**用户登录
	 * @param request
	 * @param response
	 * @return 
	 * @throws IOException 
	 */
	public String login(HttpServletRequest request,HttpServletResponse response) throws IOException{//①判断用户名和密码是否匹配②匹配后还要判断是否激活--->才能允许登录
		//获取请求参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//获取验证码------>客户端传过来的验证码
		String code = request.getParameter("code");
		//用客户端传过来的验证码跟服务器端生成的验证码进行对比,从session中取出服务器端生成的验证码
		String vcode = (String) request.getSession().getAttribute("vcode");
		
		//比较code(客户端传过来的)和vcode(服务器端生成的)
				if (vcode.equalsIgnoreCase(code)) {
					//验证码正确
					//2.调用service层的方法，处理登录业务
					//调用业务层
					UserService service=  new UserServiceImpl();
					User user = service.login(username,password);
					
					if(user != null){
						//用户名和密码匹配
						//判断是否激活
						if(user.getState() == Constant.USER_ACTIVE){
							
							//判断用户是否勾选了记住用户名
							String remeber = request.getParameter("remeber");
							//记住用户名
							Cookie cookie = new Cookie("username", user.getUsername());
							cookie.setMaxAge(60*60*24);
							cookie.setPath(request.getContextPath());
							if (!"rem".equals(remeber)) {
								//说明用户不想记住用户名了，就要以前设置好的cookie取消掉
								//如果不要记住，设置cookie的最大有效期为0
								cookie.setMaxAge(0);
							}
							response.addCookie(cookie);
							
							
							//激活, 登录成功, 保存登录状态(session)
							request.getSession().setAttribute("user", user);
							//重定向到网站首页
							response.sendRedirect(request.getContextPath()+"/index.jsp");
							return null;
							
						}else{
							//没有激活, 给用户提示
							request.setAttribute("msg", "您还没有激活,请先登录邮箱激活....");
							return "/jsp/msg.jsp";
						}
						
					
					}else {
						//错误信息存到request中
						request.setAttribute("msg", "用户名或者密码错误");
						return "/jsp/login.jsp";
					}
				}else{//TODO
					//给客户端反馈，说验证码错误
					request.setAttribute("msg", "验证码错误");
					return "jsp/msg.jsp";
				}
				
		
		
		
	
		
	}
	
	/**用户注册
	 * @param request
	 * @param response
	 * @return
	 */
	public String register(HttpServletRequest request,HttpServletResponse response){
		//获取请求参数
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, map);
			user.setUid(UUIDUtils.getId());
			user.setState(Constant.USER_NOT_ACTIVE);
			user.setCode(UUIDUtils.getCode());
			
			
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		//业务处理
		UserService serviceImpl = new UserServiceImpl();
		boolean flag = serviceImpl.regiset(user);
		//分发转向	 
		if(flag){
			request.setAttribute("msg", "注册成功!赶快去邮箱激活吧~~~");
			return "/jsp/msg.jsp";
		}else{
			request.setAttribute("msg", "注册失败!请重新注册");
			return "/jsp/msg.jsp";
		}
		
		
	}
	
	/**用户激活
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public String active(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//
		String code = request.getParameter("code");
		//
		UserService service = new UserServiceImpl();
		User user = service.active(code);
		//
		if(user != null){
			//激活成功
			response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
			return null;
		}else{
			//激活失败
			request.setAttribute("msg", "激活失败....");
			return "/jsp/msg.jsp";
		}
		
	}
	/**用户注销
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public String logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//获取session
		request.getSession().removeAttribute("user");
		response.sendRedirect(request.getContextPath()+"/index.jsp");
		return null;
		
	}

}
