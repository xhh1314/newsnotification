package cn.haiwai.newsnotification.dao.imp;

import cn.haiwai.newsnotification.dao.RoleDao;
import cn.haiwai.newsnotification.dao.bean.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleDaoImplJPA extends JpaRepository<RoleDO,Integer> {


}
