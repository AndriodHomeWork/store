package com.itcast.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itcast.bean.User;
import com.itcast.dao.UserDao;
import com.itcast.utils.C3P0Utils;

public class UserDaoImpl implements UserDao {

	@Override
	public User login(String username, String password) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql ="select * from user where username =? and password = ?";
		User user = runner.query(sql, new BeanHandler<>(User.class),username,password);
		return user;
	}

	@Override
	public boolean regiset(User u) throws Exception {
		boolean flag = false;
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		
		Object[] params={u.getUid(),u.getUsername(),u.getPassword(),u.getName(),u.getEmail(),u.getTelephone(),u.getBirthday(),u.getSex(),u.getState(),u.getCode()};
		int update = runner.update(sql, params);
		
		if(update == 1){
			flag = true;
		}
		return flag;
	}

	@Override
	public User active(String code) throws Exception {//TODO 
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from user where code =?";
		User user = runner.query(sql, new BeanHandler<>(User.class),code);
		return user;
	}

	@Override
	public void update(User u) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update user set username = ?,password = ?,name = ?,email = ?,telephone = ?,birthday =?,sex =?, state = ?,code = ? where uid = ?";
		Object[] params = {u.getUsername(),u.getPassword(),u.getName(),u.getEmail(),u.getTelephone(),u.getBirthday(),u.getSex(),u.getState(),u.getCode(),u.getUid()};
		runner.update(sql,params);
	}

}
