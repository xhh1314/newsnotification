package newsnotification.dao;

import cn.haiwai.newsnotification.NewsNotificationApplication;
import cn.haiwai.newsnotification.config.SpringCasAutoconfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = NewsNotificationApplication.class)
public class SpringCasAutoconfigTest {
    @Autowired
    private SpringCasAutoconfig casAutoconfig;
    @Test
    public void test1(){

        System.out.println("自动配置的值是:"+casAutoconfig.getLocalServerUrlPrefix());
    }
}
