package cn.haiwai.newsnotification.config;

import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器顺序：
 1. CAS Single Sign Out Filter
 2. CAS Validation Filter
 3. CAS Authentication Filter
 4. CAS HttpServletRequest Wrapper Filter
 5. CAS Assertion Thread Local Filter
 * @author hewei
 *
 */
@Configuration
public class CasFilterConfiguration {

    private static final boolean casEnabled = true;

    @Autowired
    private SpringCasAutoconfig casAutoconfig;


    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter(SpringCasAutoconfig casAutoconfig) {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new SingleSignOutFilter());
        filterRegistration.setEnabled(true);
        filterRegistration.setName("logoutFilter");
        filterRegistration.addUrlPatterns("/user/logout");
        filterRegistration.addInitParameter("casServerUrlPrefix",casAutoconfig.getCasServerUrlPrefix());
        filterRegistration.addInitParameter("serverName", casAutoconfig.getCasServerName());
        filterRegistration.setOrder(2);
        return filterRegistration;
    }

/*
    @Bean
    public FilterRegistrationBean logoutFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl(casAutoconfig.getLogoutUrl());
        filterRegistration.setFilter(logoutFilter);
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/user1/logout");
        filterRegistration.addInitParameter("casServerUrlPrefix",casAutoconfig.getCasServerUrlPrefix());
        filterRegistration.addInitParameter("serverName", casAutoconfig.getCasServerName());
        filterRegistration.setOrder(2);
        return filterRegistration;
    }*/




    /**
     * cas单点登录登出监听器
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
        listener.setEnabled(casEnabled);
        listener.setListener(new SingleSignOutHttpSessionListener());
        listener.setOrder(4);
        return listener;
    }


    /**
     * 该过滤器负责用户的认证工作
     */
    @Bean
    public FilterRegistrationBean authenticationFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new AuthenticationFilter());
        filterRegistration.setEnabled(casEnabled);
        filterRegistration.addUrlPatterns("/admin/login");
        //casServerLoginUrl:cas服务的登陆url
        filterRegistration.addInitParameter("casServerLoginUrl", casAutoconfig.getCasServerLoginUrl());
        //本项目登录ip+port
        filterRegistration.addInitParameter("serverName", casAutoconfig.getServerName());
        filterRegistration.addInitParameter("useSession", "true");
        filterRegistration.addInitParameter("redirectAfterValidation", "true");
        filterRegistration.setOrder(6);
        return filterRegistration;
    }
    /**
     * 该过滤器对HttpServletRequest请求包装， 可通过HttpServletRequest的getRemoteUser()方法获得登录用户的登录名
     *
     */
    @Bean
    public FilterRegistrationBean httpServletRequestWrapperFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new HttpServletRequestWrapperFilter());
        filterRegistration.setEnabled(casEnabled);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(7);
        return filterRegistration;
    }

    /**
     * 该过滤器使得可以通过org.jasig.cas.client.util.AssertionHolder来获取用户的登录名。
     比如AssertionHolder.getAssertion().getPrincipal().getName()。
     这个类把Assertion信息放在ThreadLocal变量中，这样应用程序不在web层也能够获取到当前登录信息
     */
    @Bean
    public FilterRegistrationBean assertionThreadLocalFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new AssertionThreadLocalFilter());
        filterRegistration.setEnabled(casEnabled);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(8);
        return filterRegistration;
    }
}
