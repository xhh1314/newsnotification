package com.haiwai.newsnotification.service;

import com.haiwai.newsnotification.dao.bean.ContentDO;
import com.haiwai.newsnotification.manage.util.TimeTransfer;

public class ContentBO implements Comparable<ContentBO>{
	
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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 实现一个降序
	 */
	@Override
	public int compareTo(ContentBO o) {
		// TODO Auto-generated method stub
		if(this.getCid()==o.getCid())
			return 0;
		return this.getCid()>o.getCid()?-1:1;
	}
	
	
	

}
