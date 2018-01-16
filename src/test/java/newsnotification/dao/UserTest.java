package newsnotification.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import cn.haiwai.newsnotification.dao.UserDao;
import cn.haiwai.newsnotification.dao.bean.ModuleDO;
import cn.haiwai.newsnotification.dao.bean.RoleDO;
import cn.haiwai.newsnotification.dao.bean.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.junit4.SpringRunner;

import cn.haiwai.newsnotification.NewsNotificationApplication;
import cn.haiwai.newsnotification.service.UserBO;
import cn.haiwai.newsnotification.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE,classes=NewsNotificationApplication.class)
public class UserTest {
	@Autowired
	private UserService us;

	@Autowired
	private UserDao userDao;

	/**
	 * 插入一个用户
	 */
	@Test
	public void test1(){
		UserBO user=new UserBO();
		user.setName("admin");
		user.setPassword("admin123!");
		try {
			us.register(user);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getUser(){
		UserDO userDO=userDao.getUser("admin");
		Set<RoleDO> roles=userDO.getRoles();
		for(RoleDO roleDO:roles){
			System.out.println(roleDO.getModules());
		}
	}
}
