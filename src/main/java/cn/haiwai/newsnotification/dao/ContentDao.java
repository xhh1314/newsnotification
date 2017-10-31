package cn.haiwai.newsnotification.dao;

import java.util.Date;
import java.util.List;

import cn.haiwai.newsnotification.dao.bean.ContentDO;

/**
 * 存储正文内容的dao类
 * @author lh
 * @time 2017年10月25日
 * @version 1.0
 */
public interface ContentDao {

	/**
	 * 插入内容
	 * 
	 * @param content
	 * @return
	 */
	ContentDO saveContent(ContentDO content);

	/**
	 * 更新正文内容
	 * 
	 * @param content
	 * @return 更新的数量
	 */
	Integer updateContent(ContentDO content);

	/**
	 * 删除一条内容
	 * 
	 * @param id
	 */
	void deleteContent(Integer id);

	/**
	 * 列示所有内容
	 * 
	 * @return
	 */
	List<ContentDO> listContent();

	/**
	 * 根据某一个日期查询内容
	 * 
	 * @param date 日期格式为yyyy-MM-dd
	 * @return
	 */
	List<ContentDO> listByDate(String date);

	/**
	 * 根据关键字查询内容
	 * 
	 * @param key
	 * @return
	 */
	List<ContentDO> listByKey(String key);

	/**
	 * 根据某一个日期查询，并且进行分页
	 * 
	 * @param date
	 * @param begin
	 *            起始行
	 * @param offset
	 *            偏移量
	 * @return
	 */
	List<ContentDO> listByDateAndLimit(Date date, int begin, int offset);

	/**
	 * 查询所有内容，并分页
	 * 
	 * @param begin
	 * @param offset
	 * @return
	 */
	List<ContentDO> listByLimit(int begin, int offset);

	/**
	 * 查询记录数
	 * 
	 * @return
	 */
	Integer countContent();

	ContentDO getContent(Integer id);

	/**
	 * 返回最近7天创建的数据,以实际创建时期为准
	 * @return
	 */
	List<ContentDO> listByRecentSevenDay();

}
