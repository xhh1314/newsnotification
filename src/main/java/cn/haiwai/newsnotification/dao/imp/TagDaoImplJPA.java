package cn.haiwai.newsnotification.dao.imp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.haiwai.newsnotification.dao.bean.TagDO;

@Repository
public interface TagDaoImplJPA extends JpaRepository<TagDO,Integer>{

	@Modifying
	@Query(value="update tag set name=?2 where id=?1",nativeQuery=true)
	Integer updateName(Integer id, String name);

	TagDO getTagByName(String name);

	TagDO getTagById(Integer id);
	

}
