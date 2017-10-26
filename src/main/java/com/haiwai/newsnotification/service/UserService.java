package com.haiwai.newsnotification.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.haiwai.newsnotification.dao.UserDao;
import com.haiwai.newsnotification.dao.bean.UserDO;
import com.haiwai.newsnotification.manage.util.Md5;


/**
 * user 操作
 * @author lh
 * @time 2017年10月25日
 * @version 
 */
@Service
@Scope("singleton")
public class UserService {
	@Autowired
	private UserDao userDao;

	/**
	 * 注册逻辑
	 * @param user
	 * @return
	 */
	public boolean register(UserBO user) {
		// TODO Auto-generated method stub
		UserDO userDO=new UserDO();
		userDO.setId(user.getId());
		userDO.setName(user.getName());
		userDO.setPassword(user.getPassword());		
		UserDO newUser=userDao.saveUser(userDO);
		return newUser==null?false:true;
	}

	
	public UserBO verificationUser(UserBO user) {
		// TODO Auto-generated method stub
		if(user==null) {
			return null;
		}
		UserDO user1=userDao.getUser(user.getName());
		if(user1==null) {
			return null;
		}
		try {
			//验证密码是否符合
			if(user1.getPassword().equals(Md5.getMd5(user.getPassword()))){
				
				return new UserBO(user1);
			} else {
				return null;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			
			 throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("编码格式不符合规范");
		}
		
	}


	public UserBO getUserByName(String name) {
		// TODO Auto-generated method stub
		UserDO userDO=userDao.getUser(name);
		return userDO==null?null:new UserBO(userDO);
	}
	
	

}
