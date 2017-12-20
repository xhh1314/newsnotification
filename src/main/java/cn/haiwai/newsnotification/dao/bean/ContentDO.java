package cn.haiwai.newsnotification.dao.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.haiwai.newsnotification.manage.util.TimeTransfer;
import cn.haiwai.newsnotification.service.ContentBO;
import cn.haiwai.newsnotification.service.TagBO;

/**
 * 存储正文的表
 * 
 * @author lh
 * @time 2017年10月25日
 * @version
 */
@Entity(name = "content")
public class ContentDO implements Serializable {

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

	/**
	 * 文章状态，0表示未发布，1表示发布
	 */
	private Integer status;

	private Set<TagDO> tags = new HashSet<TagDO>();;

	public ContentDO() {
	}

	public ContentDO(ContentBO bo) {
		this.id = bo.getCid();
		this.title = bo.getTitle();
		this.content = bo.getContent();
		this.receiveTime = TimeTransfer.stringToDate(bo.getReceiveTime());
		this.status = bo.getStatus();
		this.tags = transfer(bo.getTags());
	}

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
	@Column(name = "receive_time")
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
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 映射关系写在这里，则中间表的数据由该实体来维护, Tag方写的是mapedBy，保存Tag时不更新中间表数据
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "content_tag", joinColumns = @JoinColumn(name = "c_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "t_id", referencedColumnName = "id"))
	public Set<TagDO> getTags() {
		return tags;
	}

	public void setTags(Set<TagDO> tags) {
		this.tags = tags;
	}

	public  Set<TagDO> transfer(Set<TagBO> tags) {
		if (tags == null)
			return null;
		Set<TagDO> ts = new HashSet<TagDO>(16);
		tags.forEach(new Consumer<TagBO>() {
			@Override
			public void accept(TagBO t) {
				// TODO Auto-generated method stub
				ts.add(new TagDO(t));
			}

		});
		return ts;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContentDO [id=").append(id).append(", title=").append(title).append(", content=")
				.append(content).append(", receiveTime=").append(receiveTime).append(", createTime=").append(createTime)
				.append(", status=").append(status).append("]");
		return builder.toString();
	}

	

	
	

	

}
