package newsnotification.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import cn.haiwai.newsnotification.NewsNotificationApplication;
import cn.haiwai.newsnotification.dao.TagDao;
import cn.haiwai.newsnotification.dao.bean.TagDO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = NewsNotificationApplication.class)
public class TagTest {
	@Autowired
	private TagDao tagDao;

	@Test
	public void test1() {
		TagDO tag = new TagDO("ddd");
		tagDao.save(tag);
	}

	@Test
	public void test2() {
		System.out.println(tagDao.getTagByName("ddd").getName());

	}
}
