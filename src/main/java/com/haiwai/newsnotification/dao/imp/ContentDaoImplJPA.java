package com.haiwai.newsnotification.dao.imp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.haiwai.newsnotification.dao.bean.ContentDO;

/**
 * ContentDao jpa框架实现
 * 
 * @author lh
 * @time 2017年10月25日
 * @version
 */
@Repository
public interface ContentDaoImplJPA extends JpaRepository<ContentDO, Integer> {

	@Query(value = "select id,title,content,receive_time from content where title like %?1% or content like %?1%", nativeQuery = true)
	List<ContentDO> listByKey(String key);

	@Query(value = "select count(*) from content", nativeQuery = true)
	Integer countContent();

	@Modifying
	@Query(value = "update content set content=?2 where id=?1 ", nativeQuery = true)
	Integer updateContent(Integer id, String content);

	ContentDO getContentById(Integer id);

	@Query(value="select id,title,content,receive_time from content limit ?1 ,?2",nativeQuery=true)
	List<ContentDO> listByLimit(int begin, int offset);

}
