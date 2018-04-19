package com.itcast.dao;

import com.itcast.bean.User;

public interface UserDao {

	/**用户登录
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	User login(String username, String password) throws Exception;

	/**用户注册
	 * @param user
	 * @return
	 */
	boolean regiset(User user) throws Exception ;

	/**
	 * @param code
	 * @return
	 */
	User active(String code) throws Exception;

	/**更新用户状态
	 * @param user
	 * @throws Exception 
	 */
	void update(User user) throws Exception;

}
