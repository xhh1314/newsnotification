package cn.haiwai.newsnotification.manage.elasticsearch;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 获取elasticsearch连接的类
 * 
 * @author Administrator
 * @date 2017年11月22日
 * @version 1.0
 */
@Component
@Scope("singleton")
@ConfigurationProperties(prefix = "ElasticSearch")
public class ElasticSearchConnection {

	private static Logger logger = LoggerFactory.getLogger(ElasticSearchConnection.class);

	// 这些变量的值从application.yml配置文件读取
	private String address;

	private int httpPort;

	private String clusterName;

	private String document;

	private String type;
	private RestHighLevelClient client;
	private RestClientBuilder builder;
	private RestClient restClient;
	private final Lock lock = new ReentrantLock(true);
	
	@PostConstruct
	public void init(){
		builder=RestClient.builder(new HttpHost(address, httpPort, "http")).setMaxRetryTimeoutMillis(10000);
		restClient = builder.build();
	}

	@PreDestroy
	public void destory() {
		try {
			restClient.close();
			logger.debug("释放ElasticSearch连接！");
		} catch (IOException e) {
			logger.error("关闭restClient发生异常:{}", e);
		}
	}

	/**
	 * 这里不知道这么写对不对，目前没有看出来是否需要为每个连接都new一个新对象
	 * @return
	 */
	public RestHighLevelClient getClient() {
		// 不加锁，查看源码发现是不可变对象,成员变量final修饰
		client = new RestHighLevelClient(restClient);	
		return client;

	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
