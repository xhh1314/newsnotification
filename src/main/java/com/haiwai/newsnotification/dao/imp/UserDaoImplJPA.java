package com.haiwai.newsnotification.dao.imp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haiwai.newsnotification.dao.bean.UserDO;

@Repository
public interface UserDaoImplJPA extends JpaRepository<UserDO, Integer> {

	UserDO getUserByName(String name);

}
