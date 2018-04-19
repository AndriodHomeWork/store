package com.itcast.service;

import com.itcast.bean.User;

public interface UserService {

	/**用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	User login(String username, String password);

	/**用户注册
	 * @param user
	 * @return
	 */
	boolean regiset(User user);

	/**
	 * @param code
	 * @return
	 */
	User active(String code);

}
