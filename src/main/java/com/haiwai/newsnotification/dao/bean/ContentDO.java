package com.haiwai.newsnotification.dao.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 存储正文的表
 * 
 * @author lh
 * @time 2017年10月25日
 * @version
 */
@Entity(name = "content")
public class ContentDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7648509913944768043L;
	/**
	 * 自增id
	 */
	private Integer id;
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
	private Date receiveTime;
	/**
	 * 创建内容的时间戳
	 */
	private Date createTime;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	/**
	 * 数据库存储格式：2017-12-29
	 * 
	 * @return
	 */
	@Column(name="receive_time")
	@Temporal(TemporalType.DATE)
	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	/**
	 * 数据库存储格式：2017-12-29 16:54:04.544
	 * 
	 * @return
	 */
	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
