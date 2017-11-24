package cn.haiwai.newsnotification.manage.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.annotation.PostConstruct;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ElasticSearch")
public class ElasticSearchDataInitial {

	// 这些变量的值从application.yml配置文件读取
	private String address;

	private int tcpPort;

	private String clusterName;

	private TransportClient client = null;
	
	private String document;
	
	private String type;

	@PostConstruct
	public void init() {
		// do some initialization work
		try {
			Settings settings = Settings.builder().put("cluster.name", clusterName).build();
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(address), tcpPort));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 初始化es的document、type、mapper
	 * 
	 * @return
	 */
	
	

/*
 * curl -XPUT "http://10.1.11.28:9200/newsnotification" -H 'Content-Type: application/json' -d'
{
  "mappings":{
    "article":{
      "properties":{
        "id":{"type": "keyword"},
          "tag":{
          "type": "keyword"
        },
        "title":{"type":"text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_smart"
        },
     
        "content":{"type": "text",
                "analyzer": "ik_max_word",
                "search_analyzer": "ik_smart"
        }
      }
    }
  }
}'
 * 
 * */

	/**
	 * 这么写太麻烦了，直接curl初始化方便
	 * @return
	 */
	@Deprecated
	public boolean initialDataBase() {
		CreateIndexRequestBuilder indexBuilder=client.admin().indices().prepareCreate("newsnotification");
		/*indexBuilder.execute().actionGet();
		indexBuilder.addMapping(
				"\"article\",{"+ 
				"\"article\":"+ 
		      "\"properties\":{"+
		        "\"id\":{\"type\": \"keyword\"},"+
		          "\"tag\":{"+
		          "\"type\": \"keyword\""+
		        "},"+
		        "\"title\":{\"type\":\"text\","+
		          "\"analyzer\": \"ik_max_word\","+
		          "\"search_analyzer\": \"ik_smart\""+
		        "},"+
		      
		        "\"content\":{\"type\": \"text\","+
		                "\"analyzer\": \"ik_max_word\","+
		                "\"search_analyzer\": \"ik_smart\""+
		        "}"+
		        "}"+
		      "}");
		*/
		CreateIndexResponse indexResponse=indexBuilder.get();
		return indexResponse.isAcknowledged();

	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getTcpPort() {
		return tcpPort;
	}

	public void setTcpPort(int tcpPort) {
		this.tcpPort = tcpPort;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

}
