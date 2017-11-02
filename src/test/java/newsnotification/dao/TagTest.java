package newsnotification.dao;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import cn.haiwai.newsnotification.NewsNotificationApplication;
import cn.haiwai.newsnotification.dao.TagDao;
import cn.haiwai.newsnotification.dao.bean.TagDO;
import cn.haiwai.newsnotification.service.TagBO;

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
	@Test
	public void test3(){
		Set<TagBO> tags=new HashSet<TagBO>(16);
		tags.add(new TagBO("砥砺奋进的5年"));
		tags.add(new TagBO("十九大"));
		tags.forEach(new Consumer<TagBO>(){
			@Override
			public void accept(TagBO t) {
				// TODO Auto-generated method stub
				if(tagDao.getTagByName(t.getName())!=null)
					tags.remove(t);
			}
			
			
		});
		
	}
	
	public static void main(String[] args){
		String tag="a  dd       dssss";
		String[] tags = tag.split("\\s+");
		Set<TagBO> tagSet = new HashSet<TagBO>(16);
		for (String e : tags) {
			tagSet.add(new TagBO(e));
		}
		tagSet.remove(new TagBO());
		
	}
}
