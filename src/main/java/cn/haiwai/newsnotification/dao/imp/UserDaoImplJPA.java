package cn.haiwai.newsnotification.dao.imp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.haiwai.newsnotification.dao.bean.UserDO;

/**
 * userDao 使用spring-jpa-data实现
 * @author lh
 * @date 2017年10月31日
 * @version 
 */
@Repository
public interface UserDaoImplJPA extends JpaRepository<UserDO, Integer> {

	UserDO getUserByName(String name);

	@Modifying
	@Query(value="update users set password=?2 where id=?1",nativeQuery=true)
	Integer updatePassword(Integer id, String password);
	

}
