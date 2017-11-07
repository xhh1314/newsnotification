package cn.haiwai.newsnotification.web.controller;
import java.util.Set;
import java.util.function.Consumer;

import cn.haiwai.newsnotification.dao.bean.ContentDO;
import cn.haiwai.newsnotification.dao.bean.TagDO;
import cn.haiwai.newsnotification.manage.util.TimeTransfer;
import cn.haiwai.newsnotification.service.ContentBO;
import cn.haiwai.newsnotification.service.TagBO;

public class ContentVO implements Comparable<ContentVO>{

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

	/**
	 * 字符型的tag，接受前台json对象中的tag
	 */
	private String tags;

	/**
	 * 当向前台传输数据的时候，使用String[] 数组格式，方便json压缩
	 */
	private String[] tagArray;
	
	public ContentVO() {
	}
	public ContentVO(ContentBO content) {
		this.cid = content.getCid();
		this.content = content.getContent();
		this.title = content.getTitle();
		this.receiveTime = content.getReceiveTime();
		this.status = content.getStatus();
		transferTag(content.getTags());

	}
	public ContentVO(ContentDO content){
		this.cid=content.getId();
		this.content=content.getContent();
		this.title=content.getTitle();
		this.receiveTime=TimeTransfer.dateToLocalDate(content.getReceiveTime()).toString();
		this.status=content.getStatus();
		transferTagFromDO(content.getTags());
		
	}

	private void transferTag(Set<TagBO> tags) {
		// TODO Auto-generated method stub
		String[] tagArray=new String[tags.size()];
		StringBuffer tagText=new StringBuffer();
		
		tags.forEach(new Consumer<TagBO>(){
			int i=0;
			@Override
			public void accept(TagBO t) {
				tagArray[i]=t.getName();
				tagText.append(t.getName()+" ");
				i++;
			}
		});
		this.tags=tagText.toString();
		this.tagArray=tagArray;
	}
	private void transferTagFromDO(Set<TagDO> tags) {
		// TODO Auto-generated method stub
		String[] tagArray=new String[tags.size()];
		StringBuffer tagText=new StringBuffer();
		
		tags.forEach(new Consumer<TagDO>(){
			int i=0;
			@Override
			public void accept(TagDO t) {
				tagArray[i]=t.getName();
				tagText.append(t.getName()+"、");
				i++;
			}
		});
		this.tags=tagText.toString().substring(0,tagText.toString().length()-1);
		this.tagArray=tagArray;
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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	public String[] getTagArray() {
		return tagArray;
	}
	public void setTagArray(String[] tagArray) {
		this.tagArray = tagArray;
	}
	
	@Override
	public int compareTo(ContentVO o) {
		// TODO Auto-generated method stub
		if (this.getCid() == o.getCid())
			return 0;
		return this.getCid() > o.getCid() ? -1 : 1;
	}
	
	

}
