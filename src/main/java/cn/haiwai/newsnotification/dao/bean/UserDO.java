package cn.haiwai.newsnotification.dao.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import cn.haiwai.newsnotification.service.UserBO;

/**
 * 后台用户存储
 * 
 * @author lh
 * @time 2017年10月25日
 * @version 1.0
 */
@Entity(name = "users")
public class UserDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7594818093595889580L;
	/**
	 * 自增id
	 */
	private Integer id;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 用户创建时间戳
	 */
	private Date createTime;

	private Set<RoleDO> roles;

	public UserDO(){};
	public UserDO(UserBO user) {
		// TODO Auto-generated constructor stub
		this.name=user.getName();
		this.password=user.getPassword();
		this.id=user.getId();
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "uid",referencedColumnName = "id"),inverseJoinColumns=@JoinColumn(name="rid",referencedColumnName = "rid"))
	public Set<RoleDO> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDO> roles) {
		this.roles = roles;
	}
}
