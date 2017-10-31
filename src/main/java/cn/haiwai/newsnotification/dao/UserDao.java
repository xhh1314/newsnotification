package cn.haiwai.newsnotification.dao;

import cn.haiwai.newsnotification.dao.bean.UserDO;

/**
 * 存储user对象的dao类
 * 
 * @author lh
 * @time 2017年10月25日
 * @version 1.0
 */
public interface UserDao {
	/**
	 * 保存一个用户
	 * 
	 * @param user
	 * @return
	 */
	UserDO saveUser(UserDO user);

	/**
	 * 修改一个用户
	 * 
	 * @param user
	 * @return
	 */
	boolean updatePassword(UserDO user);

	/**
	 * 删除一个用户
	 * 
	 * @param id
	 */
	void deleteUser(Integer id);

	/**
	 * 根据用户名查询一个用户
	 * 
	 * @param name
	 * @return
	 */
	UserDO getUser(String name);

}
