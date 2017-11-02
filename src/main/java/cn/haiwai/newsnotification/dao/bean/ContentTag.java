package cn.haiwai.newsnotification.dao.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * contentTag表
 * @author lh
 * @date 2017年11月2日
 * @version 1.0 
 */
@Entity(name="content_tag")
public class ContentTag {
	
	private Integer id;
	private Integer cid;
	private Integer tid;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="c_id")
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	@Column(name="t_id")
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	

}
