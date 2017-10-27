package com.haiwai.newsnotification.service;

import com.haiwai.newsnotification.dao.bean.ContentDO;
import com.haiwai.newsnotification.manage.util.TimeTransfer;

public class ContentBO {
	
	/**
	 * 自增id
	 */
	private int cid;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章正文
	 */
	private String content;
	/**
	 * 网新办下发日期
	 */
	private String receiveTime;
	/**
	 * 文章状态
	 */
	private int status;
	
	public ContentBO(){}
	
	public ContentBO(ContentDO content){
		this.cid=content.getId();
		this.content=content.getContent();
		this.title=content.getTitle();
		this.receiveTime=TimeTransfer.dateToLocalDate(content.getReceiveTime()).toString();
		this.status=content.getStatus();
	}
	
	
	
	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	

}
