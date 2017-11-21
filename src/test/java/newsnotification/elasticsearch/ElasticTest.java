package newsnotification.elasticsearch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.get.GetField;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.ScrollableHitSource.Hit;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.haiwai.newsnotification.dao.bean.ContentDO;

@SuppressWarnings("resource")
public class ElasticTest {
	
	private static TransportClient client=null;
	static{
	try {
		client=new PreBuiltTransportClient(Settings.EMPTY)
		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.1.11.28"), 9300));
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	
	public static void main(String[] args) throws UnknownHostException{
		
		ContentDO content=new ContentDO();
		content.setContent("究天人之际");
		content.setId(2);
		content.setTitle("通古今之变");
		Map<String,String> content1=new HashMap<String,String>(16);
		content1.put("title", "究天人之际");
		content1.put("content", "通古今之变");
		content1.put("id", "1");
		
		//Settings settings = Settings.builder().put("cluster.name", "test").build();
		IndexRequestBuilder indexBuilder=client.prepareIndex("newsnotification","article","1");
		//indexBuilder.setRouting(routing);
		IndexResponse response=indexBuilder.setSource(content1).get();
		System.out.println(response.getShardInfo().getSuccessful());
		System.out.println(response.getShardInfo().getFailed());
	}
	
	/**
	 * 创建索引，并且指定mapping
	 * @throws IOException
	 */
	public void putIndexTest() throws IOException{
		 client.admin().indices().prepareCreate("producthuzhuindex").execute().actionGet();  //创建一个空索引，如没有索引，创建mapping时会报错
	        XContentBuilder mapping = XContentFactory.jsonBuilder()
	                .startObject() .startObject("producthuzhuindex").startObject("properties")
	                .startObject("plan_intro")  //嵌套对象字段
	                .startObject("properties")
	                .startObject("item").field("type", "string").field("store", "yes").field("analyzer", "ik").field("search_analyzer", "ik").endObject()
	                .startObject("content").field("type", "string").field("store", "yes").field("analyzer", "ik").field("search_analyzer", "ik").endObject()
	                .endObject()
	                .endObject()
	               .startObject("today_member").field("type", "string").field("store", "yes").endObject()   //普通字段

	   //多字段  ： 这个意思，我理解，就是一个字段有多个类型，如下这个，既有一个analyzer = id，又有一个no_analyzed   可以用于全文检索，还可以做精确查找。
	                .startObject("name").field("type", "string").field("store", "yes").field("analyzer","ik")
	                .startObject("fields").startObject("unname").field("type", "string").field("index","not_analyzed").endObject().endObject()
	                .endObject()

	                .endObject().endObject().endObject();
	        PutMappingRequest mappingRequest = Requests.putMappingRequest("producthuzhuindex").type("producthuzhuindex").source(mapping);
	        client.admin().indices().putMapping(mappingRequest).actionGet();
		
	}
	
	@Test
	@SuppressWarnings("unused")
	public void getTest1(){
		GetRequestBuilder  getBuilder=	client.prepareGet("newsnotification", "article", "1");
		GetResponse  response=getBuilder.get();
		Map<String, GetField> fields=response.getFields();
		Map<String,Object> sources=response.getSource();
		System.out.println(response.getField("title"));
		
	}
	

	@Test
	public void searchTest1() throws UnknownHostException{
		SearchRequestBuilder search=client.prepareSearch("twitter1");
		search.setTypes("tweet");
		search.setQuery(QueryBuilders.matchQuery("content", "美国美"));
		SearchResponse response=search.get();
		for(SearchHit  hit :response.getHits().getHits()){
			hit.getSource().forEach(new BiConsumer<String,Object>(){
				@Override
				public void accept(String t, Object u) {
					// TODO Auto-generated method stub
					System.out.println(t+":"+u.toString()+",\n");
				}
				
				
			});
		}
	//	search.set
		
	}
	

}
