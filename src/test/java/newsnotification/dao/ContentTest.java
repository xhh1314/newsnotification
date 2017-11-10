package newsnotification.dao;

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

	//@Test
	public void insertTest() {
		ContentDO content = new ContentDO();
		Set<TagDO> tags = new HashSet<TagDO>(8);
		tags.add(new TagDO("清风徐徐45"));
		tags.add(new TagDO("清风徐徐11"));
		//tags.remove(new TagDO("清风徐徐4"));
		//tags.add(new TagDO("砥砺前行的5年"));
		//content.setId(0);
		content.setTags(tags);
		content.setTitle("测试");
		content.setContent("在我小的时候，熬夜还是一件不常见的事情。那个时候身边的人的作息都十分有规律，");
		content.setReceiveTime(new Date());
		ContentDO cd=dao.saveContent(content);
		System.out.println(cd);
	}

	//@Test
	public void updateTest() {
		ContentBO content = new ContentBO();
		Set<TagBO> tags = new HashSet<TagBO>();
		tags.add(new TagBO("清风徐徐55"));
		tags.add(new TagBO("清风徐徐56"));
		content.setCid(new Integer(281));
		content.setTags(tags);
		content.setTitle("测试");
		content.setContent("在我小的时候，熬夜还是一件不常见的事情。那个时候身边的人的作息都十分有规律");
		content.setReceiveTime("2017-11-02");
		cs.saveContent(content);
	}

	@Test
	public void regexTest() {
		String contentDate = "2112-01-01";
		System.out.println(contentDate.matches("^[2][0]\\d{2}\\-\\d{2}\\-\\d{2}"));
	}

//	@Test
	public void bitchInsert() {
		Set<TagBO> tags = new HashSet<TagBO>();
		tags.add(new TagBO("相思"));
		tags.add(new TagBO("长叹"));
		for (int i = 0; i < 50; i++) {
			ContentBO content = new ContentBO();
			content.setTags(tags);
			content.setStatus(1);
			content.setTitle("相思" + i);
			content.setContent("落叶聚还散,寒鸦栖复惊" + i);
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
	@Test
	public void listByKey(){
		List<ContentBO> contents=cs.listContentsByKey("相思");
		System.out.println(contents);
	}
	
	

}
