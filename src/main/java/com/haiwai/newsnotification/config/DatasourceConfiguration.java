package com.haiwai.newsnotification.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;



/**
 * 数据源配置类
 * @author lh
 * @time 2017年10月25日
 * @version 
 */
@Configuration
//注解说明启用事务管理
@EnableTransactionManagement
//注解说明spring-data-jpa的实例路径
@EnableJpaRepositories("com.haiwai.newsnotification.dao.imp")
//在xml文件里配置，通过AOP管理事务，直接使用java代码配置的方式不好弄,目前内容较少，不使用aop方式
//@ImportResource("classpath:/config/TransactionAopConfig.xml")
public class DatasourceConfiguration {
			/**
			 * 使用阿里的数据源
			 * 这里数据源配置非常重要，如果数据源配置错了，会出现一个莫名奇妙的错误， 而且看不出来是数据源错了,只会提示hibernate的EntityManager初始化错误！
			 * @return
			 */
			@Primary
			@Bean
		    @ConfigurationProperties("spring.datasource")
			public DataSource dataSource() {
				return DataSourceBuilder.create().build();
			}
			
			
			
}
