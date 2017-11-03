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

	static final String sql1="select c.id,c.title,c.content,c.status,c.receive_time,t.name as tags"
			+" from content c,content_tag ct,tag t  where   c.id=ct.c_id and ct.t_id=t.id "
			+"and (c.title like %?1% or c.content like %?1% or t.name like %?1% ) and status=1";
	/**
	 * 前台模糊查询 直接根据关键字查询
	 * @param key
	 * @return 
	 */
	@Query(value = sql1, nativeQuery = true)
	List<ContentDO> listByKey(String key);

	@Query(value = "select count(*) from content", nativeQuery = true)
	Integer countContent();

	@Query(value = "select id,title,content,status,receive_time from content limit ?1 ,?2", nativeQuery = true)
	List<ContentDO> listByLimit(int begin, int offset);

	@Query(value = "select id,title,content,status,receive_time from content where receive_time=?1 and status=1", nativeQuery = true)
	List<ContentDO> listContentByDate(String date);

	@Query(value = "SELECT id,title,content,status,receive_time FROM content where create_time>=DATE_SUB(CURDATE(), INTERVAL 6 DAY) and status=1", nativeQuery = true)
	List<ContentDO> listByRecentSevenDay();

	ContentDO getContentById(Integer id);

	@Modifying
	@Query(value = "update content set content=?2 where id=?1 ", nativeQuery = true)
	Integer updateContent(Integer id, String content);

	@Modifying
	@Query(value = "update content set status=?2 where id=?1", nativeQuery = true)
	Integer updateContentStatus(int cid, int status);

}
