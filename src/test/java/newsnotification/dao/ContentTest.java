package newsnotification.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.haiwai.newsnotification.NewsNotificationApplication;
import com.haiwai.newsnotification.dao.ContentDao;
import com.haiwai.newsnotification.dao.bean.ContentDO;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE,classes=NewsNotificationApplication.class)
public class ContentTest {
	@Autowired
	private ContentDao dao;
	
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
		content.setReceiveTime(new Date());
		System.out.println(dao.updateContent(content));
		
	}

}
