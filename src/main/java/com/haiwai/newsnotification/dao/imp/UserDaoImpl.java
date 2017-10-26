package com.haiwai.newsnotification.dao.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.haiwai.newsnotification.dao.UserDao;
import com.haiwai.newsnotification.dao.bean.UserDO;

/**
 * UserDao 实现
 * 
 * @author lh
 * @time 2017年10月25日
 * @version
 */
@Repository
public class UserDaoImpl implements UserDao {
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private UserDaoImplJPA userJpa;

	@Override
	@Transactional
	public UserDO saveUser(UserDO user) {
		// TODO Auto-generated method stub
		return userJpa.save(user);
	}

	@Override
	@Transactional
	public UserDO updateUser(UserDO user) {
		// TODO Auto-generated method stub
		logger.error("该方法没有实现{}", UserDaoImpl.class);
		throw new RuntimeException("方法没有实现！");
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		logger.error("该方法没有实现{}", UserDaoImpl.class);
		throw new RuntimeException("方法没有实现！");
	}

	@Override
	@Transactional(readOnly=true)
	public UserDO getUser(String name) {
		// TODO Auto-generated method stub
		return userJpa.getUserByName(name);
	}

}
