package cn.haiwai.newsnotification.dao.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import cn.haiwai.newsnotification.service.ContentBO;
import cn.haiwai.newsnotification.service.TagBO;

/**
 * 存储标签的实体类
 * 
 * @author lh
 * @date 2017年11月1日
 * @version 1.0
 */
@Entity(name = "tag")
public class TagDO implements Serializable {

	private static final long serialVersionUID = -1781575672873333922L;
	private Integer id;
	private String name;
	

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public TagDO(String name) {
		this.name = name;
	}

	public TagDO() {
	}

	public TagDO(TagBO t) {
		this.id=t.getId();
		this.name=t.getName();
	}

	/*
	 * Set 集合存储，重写hashCode 和 equals方法
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.name!=null?this.name.hashCode():super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.name!=null?this.name.equals(((TagDO) obj).getName()):super.equals(obj);
	}

	@Override
	public String toString() {
		return "TagDO [id=" + id + ", name=" + name + "]";
	}
	private List<ContentDO> transfer(List<ContentBO> contents) {
		// TODO Auto-generated method stub
		if(contents==null)
			return null;
		List<ContentDO> cs=new ArrayList<ContentDO>(64);
		contents.forEach(new Consumer<ContentBO>(){
			@Override
			public void accept(ContentBO t) {
               // TODO Auto-generated method stub
				cs.add(new ContentDO(t));
			}
		});
		return cs;
	}

}
