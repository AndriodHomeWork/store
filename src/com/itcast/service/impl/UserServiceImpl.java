package com.itcast.service.impl;

import javax.mail.MessagingException;

import com.itcast.bean.User;
import com.itcast.constant.Constant;
import com.itcast.dao.UserDao;
import com.itcast.dao.impl.UserDaoImpl;
import com.itcast.service.UserService;
import com.itcast.utils.MailUtils;

public class UserServiceImpl implements UserService {

	@Override
	public User login(String username, String password) {
		//
		UserDao dao = new UserDaoImpl();
		User user = null;
		try {
			user = dao.login(username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean regiset(User user) {//注册有两个业务,①操作数据库插入数据.②发送邮件
		// 调用Dao, 操作数据库
		UserDao userdao = new UserDaoImpl();
		boolean flage = false;
		try {
			flage = userdao.regiset(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 发送用户激活邮件
		try {
			MailUtils.sendMail(user.getEmail(),"尊敬的"+user.getName()+":欢迎注册黑马商城!请点击下面的链接,进行激活.<a href='http://localhost:8080/day31_store/userServlet?method=active&code="+user.getCode()+"'>点击激活</a>");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return flage;
	}

	@Override
	public User active(String code) {//①根据code查找数据库中是否有这个用户 ②判断是否激活,更新用户状态
		// 调用Dao, 根据code查询用户
		UserDao dao = new UserDaoImpl();
		User user = null;
		try {
			user = dao.active(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//判断是否激活成功, 更新用户的状态
		if(user != null){
			user.setCode(null);
			user.setState(Constant.USER_ACTIVE);
			
			try {
				dao.update(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return user;
	}

}
