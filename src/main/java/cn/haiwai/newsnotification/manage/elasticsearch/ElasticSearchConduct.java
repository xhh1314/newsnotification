package cn.haiwai.newsnotification.manage.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.haiwai.newsnotification.web.controller.ContentVO;

/**
 * elasticSearch 索引的增删改查实现
 * @author Administrator
 * @date 2017年11月22日
 * @version 1.0
 */
@Component
public class ElasticSearchConduct {
	
	private static final Logger logger=LoggerFactory.getLogger(ElasticSearchConduct.class);

	@Autowired
	private ElasticSearchConnection conn;

	public void searchByKey() {
		RestHighLevelClient client = conn.getClient();

	}

	/**
	 * 新增索引
	 * @param index
	 * @param type
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public boolean insertIndex(String index, String type, ContentVO content) throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>(8);
		String sourceID = content.getCid().toString();
		jsonMap.put("id", content.getCid());
		jsonMap.put("title", content.getTitle());
		jsonMap.put("tag", content.getTagArray());
		jsonMap.put("content", content.getContent());
		jsonMap.put("receiveTime", content.getReceiveTime());
		RestHighLevelClient client = conn.getClient();
		IndexRequest indexRequest = new IndexRequest(index, type, sourceID).source(jsonMap);
		IndexResponse indexResponse = client.index(indexRequest);
		if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
           logger.trace("索引已存在，更新索引！title:{}",content.getTitle());		    
		}
		ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
		if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
			logger.trace("部分shard保存失败！");	
		}
		if (shardInfo.getFailed() > 0) {
		    for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
		        String reason = failure.reason(); 
		        logger.trace("部分shard保存失败！{}",reason);
		    }
		}
		
		return shardInfo.getSuccessful() >= 1;

	}

	/**
	 * 更新索引
	 * @param document
	 * @param type
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public boolean updateIndex(String document, String type, ContentVO content) throws IOException {
		String sourceID = content.getCid().toString();
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		{
			builder.field("id", content.getCid());
			builder.field("title", content.getTitle());
			builder.field("tag", content.getTagArray());
			builder.field("content", content.getContent());
			builder.field("receiveTime", content.getReceiveTime());
		}
		builder.endObject();
		UpdateRequest request = new UpdateRequest(document, type, sourceID).doc(builder);
		RestHighLevelClient client = conn.getClient();
		UpdateResponse updateResponse=client.update(request);
		GetResult result = updateResponse.getGetResult(); 
		ReplicationResponse.ShardInfo shardInfo = updateResponse.getShardInfo();
		if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
		    logger.trace("更新失败了{}个shard",shardInfo.getFailed()); 
		}
		if (shardInfo.getFailed() > 0) {
		    for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
		        String reason = failure.reason(); 
		        logger.trace("部分shard更新失败！{}",reason);
		    }
		}
		return shardInfo.getSuccessful()>=1;
	}
	
	/**
	 * 删除一个索引
	 * @param document
	 * @param type 
	 * @param id content.id
	 * @return
	 * @throws IOException
	 */
	public boolean deleteIndex(String document,String type,String id) throws IOException{
		DeleteRequest request = new DeleteRequest(document,type,id);
		RestHighLevelClient client = conn.getClient();
		DeleteResponse deleteResponse=client.delete(request);
		ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
		if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
		    logger.trace("部分shard删除失败! id:{}",id);
		}
		if (shardInfo.getFailed() > 0) {
		    for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
		        String reason = failure.reason(); 
		        logger.trace("部分shard删除失败！id:{} reason:{}",id,reason);
		    }
		}
		return shardInfo.getSuccessful()>=1;
	}
	
	/**
	 * 根据某一个属性进行查询，只能查询一个维度
	 * @param document
	 * @param type
	 * @param field 字段
	 * @param value 值
	 * @return
	 * @throws IOException
	 */
	public String[] searchIndexByOneField(String document,String type,String field,String value) throws IOException{
		SearchRequest searchRequest = new SearchRequest(document);
		searchRequest.types(type);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		//只返回id
		sourceBuilder.fetchSource("id", null);
		sourceBuilder.query(QueryBuilders.termQuery(field, value)); 
		searchRequest.source(sourceBuilder);
		return getResult(searchRequest);
	}
	

	/**
	 * 根据关键字搜索
	 *  title content字段里搜索关键字
	 * @param document
	 * @param type
	 * @param value 关键字
	 * @return
	 * @throws IOException 
	 */
	public String[] searchIndexByKey(String document,String type,String value) throws IOException{
		SearchRequest searchRequest=new SearchRequest(document);
		searchRequest.types(type);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.fetchSource("id", null);
		MultiMatchQueryBuilder mmb=new MultiMatchQueryBuilder(value,"title","content");
		sourceBuilder.query(mmb);
		searchRequest.source(sourceBuilder);
		return getResult(searchRequest);
	}
	
	/**
	 * 根据关键字、时间、标签搜索
	 * @param document
	 * @param type
	 * @param value
	 * @param date
	 * @param tag
	 * @return
	 * @throws IOException
	 */
	public String[] searchIndexByKeyAndDateAndTag(String document,String type,String value,String date,String tag) throws IOException{
		SearchRequest searchRequest=new SearchRequest(document);
		searchRequest.types(type);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.fetchSource("id", null);
		MultiMatchQueryBuilder mmb=new MultiMatchQueryBuilder(value,"title","content");
		TermQueryBuilder tqb1=new TermQueryBuilder("tag",tag);
		TermQueryBuilder tqb2=new TermQueryBuilder("receiveTime",date);
		BoolQueryBuilder bb=new BoolQueryBuilder();
		bb.must(mmb);
		bb.filter(tqb1);
		bb.filter(tqb2);
		sourceBuilder.query(bb);
		searchRequest.source(sourceBuilder);
		return getResult(searchRequest);
	}
	
	/**
	 * 根据关键字 标签过滤
	 * @param document
	 * @param type
	 * @param value
	 * @param tag
	 * @return
	 * @throws IOException
	 */
	public String[] searchIndexByKeyAndTag(String document,String type,String value,String tag) throws IOException{
		SearchRequest searchRequest=new SearchRequest(document);
		searchRequest.types(type);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.fetchSource("id", null);
		MultiMatchQueryBuilder mmb=new MultiMatchQueryBuilder(value,"title","content");
		TermQueryBuilder tqb1=new TermQueryBuilder("tag",tag);
		BoolQueryBuilder bb=new BoolQueryBuilder();
		bb.must(mmb);
		bb.filter(tqb1);
		sourceBuilder.query(bb);
		searchRequest.source(sourceBuilder);
		return getResult(searchRequest);
		
	}
	
	/**
	 * 根据关键字 时间过滤
	 * @param document
	 * @param type
	 * @param value
	 * @param date
	 * @return
	 * @throws IOException
	 */
	public String[] searchIndexByKeyAndDate(String document,String type,String value,String date) throws IOException{
		SearchRequest searchRequest=new SearchRequest(document);
		searchRequest.types(type);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		MultiMatchQueryBuilder mmb=new MultiMatchQueryBuilder(value,"title","content");
		sourceBuilder.fetchSource("id", null);
		//mmb.analyzer("ik_smart");
		TermQueryBuilder tqb1=new TermQueryBuilder("receiveTime",date);
		BoolQueryBuilder bb=new BoolQueryBuilder();
		bb.must(mmb);
		bb.filter(tqb1);
		sourceBuilder.query(bb);
		searchRequest.source(sourceBuilder);
		return getResult(searchRequest);
		
	}
	
	/**
	 * 执行查询，返回一个id集合
	 * @param searchRequest
	 * @return
	 * @throws IOException
	 */
	private String[] getResult(SearchRequest searchRequest) throws IOException{
		RestHighLevelClient client = conn.getClient(); 
		SearchResponse searchResponse = client.search(searchRequest);
		SearchHits hits = searchResponse.getHits();
		long total=hits.getTotalHits();
		String[] ids=new String[(int)total];
		List<Map<String,Object>> sources=new ArrayList<Map<String,Object>>(16);
		SearchHit[] searchHits = hits.getHits();
		int i=0;
		for (SearchHit hit : searchHits) {
		    ids[i++]=hit.getId();
		   sources.add(hit.getSourceAsMap());
		}
		return ids;
	}
	
	
	

}
