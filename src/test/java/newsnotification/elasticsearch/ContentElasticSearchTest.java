package newsnotification.elasticsearch;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import cn.haiwai.newsnotification.NewsNotificationApplication;
import cn.haiwai.newsnotification.manage.elasticsearch.ElasticSearchConduct;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = NewsNotificationApplication.class)
public class ContentElasticSearchTest {
	
	@Autowired
	private ElasticSearchConduct esc;
	
	/**
	 * 根据某一个维度查询
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	@Test
	public void serarchTest1() throws IOException{
		String[] ids=esc.searchIndexByOneField("newsnotification","article" , "content", "项目");
	}
	
	@Test
	public void multilSearchTest2() throws IOException{
		
		esc.searchIndexByKey("newsnotification","article","项目");
	}
	@Test
	public void multilSearchTest3() throws IOException{
		esc.searchIndexByKeyAndDate("newsnotification","article","习近平","2017-11-23");
	}

}
