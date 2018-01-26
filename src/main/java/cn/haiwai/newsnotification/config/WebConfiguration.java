package cn.haiwai.newsnotification.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.haiwai.newsnotification.web.filter.EncodingFilter;
import cn.haiwai.newsnotification.web.filter.PermissionInterceptor;


/**
 * springmvc配置类
 * @author lh
 * @time 2017年10月25日
 * @version 1.0
 */
@Configuration
public class WebConfiguration  extends WebMvcConfigurerAdapter{
		/*
		 *注册拦截器
		 *  (non-Javadoc)
		 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
		 */
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			super.addInterceptors(registry);
			//registry.addInterceptor(new PermissionInterceptor()).addPathPatterns("/admin/*");
			registry.addInterceptor(new EncodingFilter()).addPathPatterns("/**");
		}


		
		/* 
		 * 注册资源路径
		 * (non-Javadoc)
		 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
		 */
		@Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/staic/**").addResourceLocations("/static/");
			registry.addResourceHandler("/staic/**").addResourceLocations("/templates/static/");
		}


}
