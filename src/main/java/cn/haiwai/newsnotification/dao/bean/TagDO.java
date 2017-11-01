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
	/**
	 * 所属这个标签的集合
	 */
	private List<ContentDO> contents = new ArrayList<ContentDO>(64);

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

	@ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public List<ContentDO> getContents() {
		return contents;
	}

	public void setContents(List<ContentDO> contents) {
		this.contents = contents;
	}

	public TagDO(String name) {
		this.name = name;
	}

	public TagDO() {
	}

	public TagDO(TagBO t) {
		this.id=t.getId();
		this.name=t.getName();
		this.contents=transfer(t.getContents());
	}

	/*
	 * Set 集合存储，重写hashCode 和 equals方法
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.name.equals(((TagDO) obj).getName());
	}

	@Override
	public String toString() {
		return "TagDO [id=" + id + ", name=" + name + ", contents=" + contents + "]";
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
