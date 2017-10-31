package newsnotification.dao;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import cn.haiwai.newsnotification.NewsNotificationApplication;
import cn.haiwai.newsnotification.dao.ContentDao;
import cn.haiwai.newsnotification.dao.bean.ContentDO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE,classes=NewsNotificationApplication.class)
public class JsoupTest {
	@Autowired
	private ContentDao contentDao;
	
	@Test
	public void test1(){
		ContentDO content=contentDao.getContent(102);
		Document doc = Jsoup.parse(content.getContent());
		String str=doc.text();
		str.replaceAll("\\s\\n\\r", "");
		System.out.println(str);
		
		
	}
	
	public StringBuilder parseElement(Node node){
		List<Node> nodes=node.childNodes();
		if(!nodes.isEmpty()){
			for(Node n:nodes){
				parseElement(n);
			}
			
		}
		return null;
		
	}
	
	

}
