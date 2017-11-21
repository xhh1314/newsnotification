package cn.haiwai.newsnotification.dao.imp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.haiwai.newsnotification.dao.bean.ContentDO;

/**
 * ContentDao jpa框架实现
 * 
 * @author lh
 * @time 2017年10月25日
 * @version
 */
@Repository
public interface ContentDaoImplJPA extends JpaRepository<ContentDO, Integer> {

	static final String SQL1 = "select c.id,c.title,c.content,c.status,c.receive_time,t.name as tags"
			+ " from content c,content_tag ct,tag t  where   c.id=ct.c_id and ct.t_id=t.id "
			+ "and (c.title like %?1% or c.content like %?1% or t.name like %?1% ) and status=1";

	/**
	 * 前台模糊查询 直接根据关键字查询
	 * 
	 * @param key
	 * @return
	 */
	@Query(value = SQL1, nativeQuery = true)
	List<ContentDO> listByKey(String key);

	@Query(value = "select count(*) from content", nativeQuery = true)
	Integer countContent();

	@Query(value = "select id,title,content,status,receive_time from content limit ?1 ,?2", nativeQuery = true)
	List<ContentDO> listByLimit(int begin, int offset);

	@Query(value = "select id,title,content,status,receive_time from content where receive_time=?1 and status=1", nativeQuery = true)
	List<ContentDO> listContentByDate(String date);

	@Query(value = "SELECT id,title,content,status,receive_time FROM content where receive_time>=DATE_SUB(CURDATE(), INTERVAL 6 DAY) and status=1", nativeQuery = true)
	List<ContentDO> listByRecentSevenDay();

	ContentDO getContentById(Integer id);

	@Modifying
	@Query(value = "update content set content=?2 where id=?1 ", nativeQuery = true)
	Integer updateContent(Integer id, String content);

	@Modifying
	@Query(value = "update content set status=?2 where id=?1", nativeQuery = true)
	Integer updateContentStatus(int cid, int status);

	/**
	 * 删除content表数据时，同时删除中间表数据
	 * 
	 * @param id
	 */
	@Modifying
	@Query(value = "delete from content_tag where c_id=?1", nativeQuery = true)
	void deleteContentTag(Integer id);

	// @Query(value="",nativeQuery=true)
	static final String SQL2 = "select c.id,c.title,c.content,c.status,c.receive_time,t.name as tags"
			+ " from content c,content_tag ct,tag t  where   c.id=ct.c_id and ct.t_id=t.id "
			+ "and (c.title like %?1% or c.content like %?1% ) and c.receive_time=?2 and t.name=?3 and status=1";

	@Query(value = SQL2, nativeQuery = true)
	List<ContentDO> listByKeyAndDateAndTag(String word, String date, String tag);

	static final String SQL3 = "select c.id,c.title,c.content,c.status,c.receive_time,t.name as tags"
			+ " from content c,content_tag ct,tag t  where   c.id=ct.c_id and ct.t_id=t.id "
			+ " and c.receive_time=?1 and t.name=?2 and status=1";

	@Query(value = SQL3, nativeQuery = true)
	List<ContentDO> listByDateAndTag(String date, String tag);

	static final String SQL4 = "select c.id,c.title,c.content,c.status,c.receive_time,t.name as tags"
			+ " from content c,content_tag ct,tag t  where   c.id=ct.c_id and ct.t_id=t.id "
			+ "and (c.title like %?1% or c.content like %?1% ) and t.name=?2 and status=1";

	@Query(value = SQL4, nativeQuery = true)
	List<ContentDO> ListByKeyAndTag(String word, String tag);

	static final String SQL5 = "select c.id,c.title,c.content,c.status,c.receive_time,t.name as tags"
			+ " from content c,content_tag ct,tag t  where   c.id=ct.c_id and ct.t_id=t.id "
			+ "and (c.title like %?1% or c.content like %?1% or t.name like %?1% ) and c.receive_time=?2 and status=1";

	@Query(value = SQL5, nativeQuery = true)
	List<ContentDO> listByKeyAndDate(String word, String date);

	static final String SQL6 = "select c.id,c.title,c.content,c.status,c.receive_time,t.name as tags"
			+ " from content c,content_tag ct,tag t  where   c.id=ct.c_id and ct.t_id=t.id "
			+ " and  t.name=?1 and status=1 limit ?2,?3 ";
	@Query(value = SQL6, nativeQuery = true)
	List<ContentDO> listByTagAndLimit(String tag, int i, int j);

}
