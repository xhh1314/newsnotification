package cn.haiwai.newsnotification.dao;

import java.util.Date;
import java.util.List;

import cn.haiwai.newsnotification.dao.bean.ContentDO;

/**
 * 存储正文内容的dao类
 * 
 * @author lh
 * @time 2017年10月25日
 * @version 1.0
 */
public interface ContentDao {

	/**
	 * 插入内容 插入之前检查tag是否存在，同时检查content.id是否数据库存在，如果存在则update
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
	 * @param date
	 *            日期格式为yyyy-MM-dd
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
	 * 
	 * @return
	 */
	List<ContentDO> listByRecentSevenDay();

	/**
	 * 更新content的status
	 * 
	 * @param cid
	 * @param status
	 * @return 更新数量
	 */
	Integer updateContentStatus(int cid, int status);

	/**
	 * 以 关键字 时间 标签三个维度查询
	 * 
	 * @param word
	 * @param date
	 * @param tag
	 * @return
	 */
	List<ContentDO> listByKeyAndDateAndTag(String word, String date, String tag);

	/**
	 * 以时间 标签两个维度查询
	 * 
	 * @param date
	 * @param tag
	 * @return
	 */
	List<ContentDO> listByDateAndTag(String date, String tag);

	/**
	 * 以关键字 标签两个维度查询
	 * 
	 * @param word
	 * @param tag
	 * @return
	 */
	List<ContentDO> ListByKeyAndTag(String word, String tag);

	/**
	 * 以关键字 时间两个维度查询
	 * 
	 * @param word
	 * @param date
	 * @return
	 */
	List<ContentDO> listByKeyAndDate(String word, String date);

	/**
	 * 以标签为维度查询 同时分页
	 * 
	 * @param tag
	 * @param i
	 *            起始位置
	 * @param j
	 *            偏移量
	 * @return
	 */
	List<ContentDO> listByTagAndLimit(String tag, int i, int j);

	/**
	 * 根据id集合查询数据
	 * 
	 * @param ids
	 * @return
	 */
	List<ContentDO> listByIdArray(String[] ids);

}
