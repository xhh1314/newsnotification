package cn.haiwai.newsnotification.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;

import cn.haiwai.newsnotification.dao.bean.ContentDO;
import cn.haiwai.newsnotification.dao.bean.TagDO;
import cn.haiwai.newsnotification.manage.util.TimeTransfer;
import cn.haiwai.newsnotification.web.controller.ContentVO;

public class ContentBO implements Comparable<ContentBO> {

	/**
	 * 自增id
	 */
	private Integer cid;
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
	private Integer status;

	private Set<TagBO> tags = new HashSet<TagBO>();

	public ContentBO() {
	}

	public ContentBO(ContentDO content) {
		this.cid = content.getId();
		this.content = content.getContent();
		//业务允许正文为空，如果正文无内容，则前台显示的时候，补上一段说明，以美观
		if(!StringUtils.hasText(this.content))
			this.content="无更多内容";
		this.title = content.getTitle();
		this.receiveTime = TimeTransfer.dateToLocalDate(content.getReceiveTime()).toString();
		this.status = content.getStatus();
		this.tags = transfer(content.getTags());
	}
	public ContentBO(ContentVO content){
		this.cid = content.getCid();
		this.content = content.getContent();
		this.title = content.getTitle();
		this.receiveTime = content.getReceiveTime();
		this.status = content.getStatus();
		this.tags = transferTag(content.getTags());
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<TagBO> getTags() {
		return tags;
	}

	public void setTags(Set<TagBO> tags) {
		this.tags = tags;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object) 实现一个降序
	 */
	@Override
	public int compareTo(ContentBO o) {
		// TODO Auto-generated method stub
		if (this.getCid() == o.getCid())
			return 0;
		return this.getCid() > o.getCid() ? -1 : 1;
	}

	private Set<TagBO> transfer(Set<TagDO> tags) {
		if (tags == null)
			return null;
		Set<TagBO> ts = new HashSet<TagBO>(16);
		for (TagDO e : tags) {
			ts.add(new TagBO(e));
		}

		return ts;
	}
	
	private Set<TagBO> transferTag(String tag){
		String[] tags = tag.split("\\s+");
		Set<TagBO> tagSet = new HashSet<TagBO>(16);
		for (String e : tags) {
			tagSet.add(new TagBO(e));
		}
		// 把拆分出来的tag数组存入content
		return tagSet;
	}

}
