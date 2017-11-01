package cn.haiwai.newsnotification.service;

import java.util.ArrayList;
import java.util.List;

import cn.haiwai.newsnotification.dao.bean.ContentDO;
import cn.haiwai.newsnotification.dao.bean.TagDO;

/**
 * tag 的BO类
 * 
 * @author lh
 * @date 2017年11月1日
 * @version 1.0
 */
public class TagBO {

	private Integer id;
	private String name;
	private List<ContentBO> contents;

	public TagBO() {
	}

	public TagBO(TagDO tag) {
		this.id = tag.getId();
		this.name = tag.getName();
		this.contents = transfer(tag.getContents());
	}

	public TagBO(String name) {
		this.name = name;
	}

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

	public List<ContentBO> getContents() {
		return contents;
	}

	public void setContents(List<ContentBO> contents) {
		this.contents = contents;
	}

	private List<ContentBO> transfer(List<ContentDO> contents) {
		if(contents==null)
			return null;
		List<ContentBO> cbs = new ArrayList<ContentBO>(64);
		for (ContentDO e : contents) {

			cbs.add(new ContentBO(e));
		}
		return cbs;
	}

	@Override
	public String toString() {
		return "TagBO [id=" + id + ", name=" + name + ", contents=" + contents + "]";
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.name.equals(((TagBO) obj).getName());
	}

}
