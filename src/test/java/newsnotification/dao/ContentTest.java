package newsnotification.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import cn.haiwai.newsnotification.NewsNotificationApplication;
import cn.haiwai.newsnotification.dao.ContentDao;
import cn.haiwai.newsnotification.dao.bean.ContentDO;
import cn.haiwai.newsnotification.dao.bean.TagDO;
import cn.haiwai.newsnotification.service.ContentBO;
import cn.haiwai.newsnotification.service.ContentService;
import cn.haiwai.newsnotification.service.TagBO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = NewsNotificationApplication.class)
public class ContentTest {
	@Autowired
	private ContentDao dao;
	@Autowired
	private ContentService cs;

	@Test
	public void insertTest() {
		ContentDO content = new ContentDO();
		List<TagDO> tags = new ArrayList<TagDO>(6);
		tags.add(new TagDO("十九大"));
		tags.add(new TagDO("砥砺奋进的5年"));
		// content.setTags(tags);
		content.setTitle("测试");
		content.setContent("在我小的时候，熬夜还是一件不常见的事情。那个时候身边的人的作息都十分有规律，");
		content.setReceiveTime(new Date());
		dao.saveContent(content);
	}

	@Test
	public void updateTest() {
		ContentDO content = new ContentDO();
		content.setId(1);
		content.setTitle("测试");
		content.setContent("在我小的时候，熬夜还是一件不常见的事情。那个时候身边的人的作息都十分有规律，");
		// content.setReceiveTime(new Date());
		System.out.println(dao.updateContent(content));

	}

	@Test
	public void regexTest() {
		String contentDate = "2112-01-01";

		System.out.println(contentDate.matches("^[2][0]\\d{2}\\-\\d{2}\\-\\d{2}"));
	}

	@Test
	public void bitchInsert() {
		Set<TagBO> tags = new HashSet<TagBO>();
		tags.add(new TagBO("十九大"));
		tags.add(new TagBO("砥砺奋进的5年"));
		for (int i = 0; i < 10; i++) {
			ContentBO content = new ContentBO();
			content.setTags(tags);
			content.setTitle("阳光明媚的一天" + i);
			content.setContent("在我小的时候，熬夜还是一件不常见的事情。那个时候身边的人的作息都十分有规律；秋尽叶黄，暗香盈袖" + i);
			content.setReceiveTime("2017-10-29");
			cs.saveContent(content);
		}
	}

	@Test
	public void listAll() {
		@SuppressWarnings("unused")
		List<ContentDO> contents = dao.listContent();
		System.out.println();
	}

}
