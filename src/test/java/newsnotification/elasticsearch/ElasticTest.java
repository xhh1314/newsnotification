/*package newsnotification.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSON;

import cn.haiwai.newsnotification.dao.bean.ContentDO;

public class ElasticTest {
	
	public static void main(String[] args) throws UnknownHostException{
		
		ContentDO content=new ContentDO();
		content.setContent("究天人之际");
		content.setId(2);
		content.setTitle("通古今之变");
		Map<String,String> content1=new HashMap<String,String>(16);
		content1.put("title", "究天人之际");
		content1.put("content", "通古今之变");
		content1.put("id", "1");
		
		Settings settings = Settings.builder().put("cluster.name", "test").build();
		TransportClient client = new PreBuiltTransportClient(settings)
		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
		
		IndexResponse response=client.prepareIndex("newsnotification","article","1").setSource(content1).get();
	}

}
*/