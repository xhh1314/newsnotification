package newsnotification.dao;

import java.time.LocalDate;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import cn.haiwai.newsnotification.NewsNotificationApplication;
import cn.haiwai.newsnotification.dao.ContentDao;
import cn.haiwai.newsnotification.dao.bean.ContentDO;
import cn.haiwai.newsnotification.service.ContentBO;
import cn.haiwai.newsnotification.service.ContentService;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE,classes=NewsNotificationApplication.class)
public class ContentTest {
	@Autowired
	private ContentDao dao;
	@Autowired
	private ContentService cs;
	
	@Test
	public void insertTest(){
		ContentDO content=new ContentDO();
		content.setTitle("测试");
		content.setContent("在我小的时候，熬夜还是一件不常见的事情。那个时候身边的人的作息都十分有规律，");
		content.setReceiveTime(new Date());
		dao.saveContent(content);
	}
	@Test
	public void updateTest() {
		ContentDO content=new ContentDO();
		content.setId(1);
		content.setTitle("测试");
		content.setContent("在我小的时候，熬夜还是一件不常见的事情。那个时候身边的人的作息都十分有规律，");
		//content.setReceiveTime(new Date());
		System.out.println(dao.updateContent(content));
		
	}
	@Test
	public void regexTest(){
		String contentDate="2112-01-01";
		
		System.out.println(contentDate.matches("^[2][0]\\d{2}\\-\\d{2}\\-\\d{2}"));
	}
	@Test
	public void bitchInsert() {
		for(int i=0;i<100;i++) {
		ContentBO content=new ContentBO();
		content.setTitle("阳光明媚的一天"+i);
		content.setContent("在我小的时候，熬夜还是一件不常见的事情。那个时候身边的人的作息都十分有规律；秋尽叶黄，暗香盈袖"+i);
		content.setReceiveTime("2017-10-29");
		cs.saveContent(content);
		}
	}

}
