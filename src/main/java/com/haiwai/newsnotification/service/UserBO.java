package com.haiwai.newsnotification.service;

import com.haiwai.newsnotification.dao.bean.UserDO;

public class UserBO {
	private int id;
	private String name;
	private String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserBO() {}
	public UserBO(UserDO user) {
		this.id=user.getId();
		this.name=user.getName();
		this.password=user.getPassword();
	}

}
