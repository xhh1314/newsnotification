package cn.haiwai.newsnotification.config;

import cn.haiwai.newsnotification.service.MyShiroRealm;
import cn.haiwai.newsnotification.web.filter.EncodingServletFilter;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
@Configuration
@Component
@EnableConfigurationProperties(SpringCasAutoconfig.class)
public class ShiroConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    //TODO 这里从配置文件读取数据有一个大坑
    /**
     * 使用@Bean 注解方式注入值，需要从配置文件读取属性，总是抱nep异常，看原因应该就是在引用 SpringCasAutoconfig的时候，SpringCasAutoconfig并没有被注入
     * 这个问题不知道什么原因，搞了很久也没有搞好，所以不能使用@Autowired方式声明然后去调用
     * 解决办法就是，注入bean的时候，在方法参数里，显式的声明这个bean,保证初始化@Bean的时候，SpringCasAutoconfig先被实例化，即先从配置文件读取数据
     */
    @Autowired
    private SpringCasAutoconfig casAutoconfig;


    private String casServerName = "https://sso.lihao.com:8443/cas";
    /**
     * https://sso.lihao.com:8443/cas
     */
    // @Value("${cas.casServerUrlPrefix}")
    private String casServerUrlPrefix = "https://sso.lihao.com:8443/cas";
    /**
     * https://sso.lihao.com:8443/cas/login
     */
    // @Value("${cas.casServerLoginUrl}")
    private String casServerLoginUrl = casServerUrlPrefix + "/login";
    /**
     * https://sso.lihao.com:8443/cas/logout
     */
    // @Value("${cas.casServerLogoutUrl}")
    private String casServerLogoutUrl = casServerUrlPrefix + "/logout";
    /**
     * http://localhost:8080
     */
    // @Value("${cas.localServerUrlPrefix}")
    private String localServerUrlPrefix = "http://localhost:8082";
    //private String localServerUrlPrefix=CasAutoconfig.getLocalServerUrlPrefix();
    /**
     * /shiro-cas
     */
    //  @Value("${cas.casFilterUrlPattern}")
    private String casFilterUrlPattern = "/shiro-cas";
    // @Value("${cas.serverName}")
    private String serverName;
    // @Value("${cas.useSession}")
    private boolean useSession = true;
    //  @Value("${cas.redirectAfterValidation}")
    private boolean redirectAfterValidation = true;
    // @Value("${cas.localServerLoginUrl}")
    private String localServerLoginUrl = localServerUrlPrefix + "/user/login";

    private String loginUrl = casServerLoginUrl + "?service=" + localServerUrlPrefix + casFilterUrlPattern;

    private String logoutUrl = casServerLogoutUrl + "?service=" + localServerUrlPrefix + "/admin/login";

    private String loginFailUrl = "/user/403";
    private String loginSuccessUrl = "/admin/index";

    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }

    /**
     * 字符编码拦截器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean encodingServletFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new EncodingServletFilter());
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/**");
        filterRegistration.setOrder(1);
        return filterRegistration;
    }


    /**
     * 注册DelegatingFilterProxy（Shiro）
     * 集成Shiro有2种方法：
     * 1. 按这个方法自己组装一个FilterRegistrationBean（这种方法更为灵活，可以自己定义UrlPattern，
     * 在项目使用中你可能会因为一些很但疼的问题最后采用它， 想使用它你可能需要看官网或者已经很了解Shiro的处理原理了）
     * 2. 直接使用ShiroFilterFactoryBean（这种方法比较简单，其内部对ShiroFilter做了组装工作，无法自己定义UrlPattern，
     * 默认拦截 /*）
     *
     * @return
     * @author SHANHY
     * @create 2016年1月13日
     */

    //TODO 这个拦截器需要注意中文乱码问题！所以这里配置的时候，特地把字符编码拦截器放到了这里，并且把order设置为1，以提醒自己
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.setOrder(5);
        filterRegistration.addUrlPatterns("/*");// 可以自己灵活的定义很多，避免一些根本不需要被Shiro处理的请求被包含进来
        return filterRegistration;
    }

    /**
     * 配置自定义的权限登录器
     *
     * @param cacheManager
     * @param matcher
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm(EhCacheManager cacheManager, @Qualifier("credentialsMatcher") CredentialsMatcher matcher, @Qualifier("casAutoconfig") SpringCasAutoconfig casAutoconfig) {
        MyShiroRealm realm = new MyShiroRealm();
        // this is the url of the CAS server (example : http://host:port/cas)
        realm.setCasServerUrlPrefix(casAutoconfig.getCasServerUrlPrefix());
        // this is the CAS service url of the application (example : http://host:port/mycontextpath/shiro-cas)
        realm.setCasService(casAutoconfig.getLocalServerUrlPrefix() + casAutoconfig.getCasFilterUrlPattern());
        realm.setDefaultRoles("admin");
        realm.setCacheManager(cacheManager);
        //TODO 集成cas后不需要密码验证?
        // realm.setCredentialsMatcher(matcher);
        return realm;
    }

    //配置自定义的密码比较器
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        //集成cas后这个密码比较器应该就用不着了，不过应该修改cas源码，指定这个密码加密算法
        CredentialsMatcher credentialsMatcher = new cn.haiwai.newsnotification.config.CredentialsMatcher();
        return credentialsMatcher;
    }

    @Bean(name = "securityManager")
    public SecurityManager getDefaultWebSecurityManager(MyShiroRealm myShiroRealm,CasSubjectFactory casSubjectFactory) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(myShiroRealm);
//      <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->
        dwsm.setCacheManager(getEhCacheManager());
        dwsm.setSubjectFactory(casSubjectFactory);
        return dwsm;
    }

    @Bean
    public CasSubjectFactory casSubjectFactory(){
        return new CasSubjectFactory();
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

   /* @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }*/

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(SecurityManager securityManager){
        MethodInvokingFactoryBean methodInvokingFactoryBean=new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        Object[] objects=new Object[1];
        objects[0]=securityManager;
        methodInvokingFactoryBean.setArguments(objects);
        return methodInvokingFactoryBean;
    }

    /**
     * CAS过滤器
     *
     * @return
     */
    @Bean(name = "casFilter")
    public CasFilter getCasFilter(SpringCasAutoconfig casAutoconfig) {
        CasFilter casFilter = new CasFilter();
        casFilter.setName("casFilter");
        casFilter.setEnabled(true);
        casFilter.setSuccessUrl(casAutoconfig.getLoginSuccessUrl());
        // 登录失败后跳转的URL，也就是 Shiro 执行 CasRealm 的 doGetAuthenticationInfo 方法向CasServer验证tiket
        // 我们选择认证失败后再打开登录页面
        casFilter.setFailureUrl(casAutoconfig.getLoginFailUrl());
        return casFilter;
    }


    //TODO 原来测试的时候，自己手动注入了一个logotuFilter，结果是每次一登录就注销了，后来总结原因，极有可能是这个代码放的位置不对，
    //导致拦截顺序不对
   // @Bean
    public LogoutFilter logoutFilter(SpringCasAutoconfig casAutoconfig) {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl(casAutoconfig.getLogoutUrl());
        return logoutFilter;
    }

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     *
     * @author SHANHY
     * @create 2016年1月14日
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean, SpringCasAutoconfig casAutoconfig) {
        /////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        logger.info("##################从数据库读取权限规则，加载到shiroFilter中##################");
        // filterChainDefinitionMap.put("/user/edit/**", "authc,perms[user:edit]");
        // anon：它对应的过滤器里面是空的,什么都没做
        // 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取
        //TODO 务必注意这里的拦截器顺序，特殊路径应该配置在前面，比如/*这样的路径应该配置在拦截器后边,所以封装对象的时候应该用Link来存，保证有序
        // shiro集成cas后，首先添加该规则
        filterChainDefinitionMap.put(casAutoconfig.getCasFilterUrlPattern(), "casFilter");
        filterChainDefinitionMap.put("/user/logout", "logout");
        filterChainDefinitionMap.put("/admin/login", "anon");
        filterChainDefinitionMap.put("/admin/index", "roles[admin,customer]");
        filterChainDefinitionMap.put("/admin/contentEdit", "perms[admin:add]");
        filterChainDefinitionMap.put("/admin/**", "authc");
        filterChainDefinitionMap.put("/**", "anon");//anon 可以理解为不拦截
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    /**
     * ShiroFilter<br/>
     * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
     * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
     *
     * @return
     * @author SHANHY
     * @create 2016年1月14日
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager, CustomRolesAuthorizationFilter customRolesAuthorizationFilter, CasFilter casFilter, SpringCasAutoconfig casAutoconfig) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl(casAutoconfig.getLoginUrl());
        // 登录成功后要跳转的连接
        //TODO 集成cas后会导致循环重定向？
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/403");
        // Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
        Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>(4);
        filterMap.put("casFilter", casFilter);
        //filterMap.put("logoutFilter", logoutFilter);

        //重写roles判断器，具体含义请看@CustomRolesAuthorizationFilter注解描述
        filterMap.put("roles", customRolesAuthorizationFilter);
        shiroFilterFactoryBean.setFilters(filterMap);
        loadShiroFilterChain(shiroFilterFactoryBean, casAutoconfig);
        return shiroFilterFactoryBean;
    }


}
