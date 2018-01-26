package cn.haiwai.newsnotification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * 这里的属性如果直接从.yml文件加载，@Value("${cas.casServerUrlPrefix}") 后面的shiroConfiguration,注入bean的属性的时候，会注入null值，应该是加载顺序问题
 * 所以，这里的属性，单独放到application.properties文件，使用ConfigurationProperties的方式加载
 */
@ConfigurationProperties(prefix = "cas")
@Component("casAutoconfig")
public class SpringCasAutoconfig {

    private String validateFilters;
    private String signOutFilters;
    private String authFilters;
    private String assertionFilters;
    private String requestWrapperFilters;

    @Value("casServerName")
    private String casServerName;
    /**
     * https://sso.lihao.com:8443/cas
     */
    @Value("casServerUrlPrefix")
    private String casServerUrlPrefix;
    /**
     * https://sso.lihao.com:8443/cas/login
     */
    @Value("casServerLoginUrl")
    private String casServerLoginUrl;
    /**
     * https://sso.lihao.com:8443/cas/logout
     */
    @Value("casServerLogoutUrl")
    private String casServerLogoutUrl;
    /**
     * http://localhost:8080
     */
    @Value("localServerUrlPrefix")
    private String localServerUrlPrefix;
    /**
     * /shiro-cas
     */
    @Value("casFilterUrlPattern")
    private String casFilterUrlPattern;
    @Value("serverName")
    private String serverName;
    /**
     * http://sso.lihao.com:8443/cas/login?service=http://localhost:8080/admin/index
     */
    @Value("loginUrl")
    private String loginUrl;

    @Value("logoutUrl")
    private String logoutUrl;

    @Value("loginSuccessUrl")
    private String loginSuccessUrl;
    @Value("loginFailUrl")
    private String loginFailUrl;

    @Value("useSession")
    private String useSession ;
    @Value("redirectAfterValidation")
    private String redirectAfterValidation;
    @Value("localServerLoginUrl")
    private String localServerLoginUrl;


    public String getCasServerName() {
        return casServerName;
    }

    public void setCasServerName(String casServerName) {
        this.casServerName = casServerName;
    }

    public String getValidateFilters() {
        return validateFilters;
    }

    public void setValidateFilters(String validateFilters) {
        this.validateFilters = validateFilters;
    }

    public String getSignOutFilters() {
        return signOutFilters;
    }

    public void setSignOutFilters(String signOutFilters) {
        this.signOutFilters = signOutFilters;
    }

    public String getAuthFilters() {
        return authFilters;
    }

    public void setAuthFilters(String authFilters) {
        this.authFilters = authFilters;
    }

    public String getAssertionFilters() {
        return assertionFilters;
    }

    public void setAssertionFilters(String assertionFilters) {
        this.assertionFilters = assertionFilters;
    }

    public String getRequestWrapperFilters() {
        return requestWrapperFilters;
    }

    public void setRequestWrapperFilters(String requestWrapperFilters) {
        this.requestWrapperFilters = requestWrapperFilters;
    }

    public String getCasServerUrlPrefix() {
        return casServerUrlPrefix;
    }

    public void setCasServerUrlPrefix(String casServerUrlPrefix) {
        this.casServerUrlPrefix = casServerUrlPrefix;
    }

    public String getCasServerLoginUrl() {
        return casServerLoginUrl;
    }

    public void setCasServerLoginUrl(String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String isUseSession() {
        return useSession;
    }

    public void setUseSession(String useSession) {
        this.useSession = useSession;
    }

    public String isRedirectAfterValidation() {
        return redirectAfterValidation;
    }

    public void setRedirectAfterValidation(String redirectAfterValidation) {
        this.redirectAfterValidation = redirectAfterValidation;
    }

    public String getLocalServerUrlPrefix() {
        return localServerUrlPrefix;
    }

    public void setLocalServerUrlPrefix(String localServerUrlPrefix) {
        this.localServerUrlPrefix = localServerUrlPrefix;
    }

    public String getCasFilterUrlPattern() {
        return casFilterUrlPattern;
    }

    public void setCasFilterUrlPattern(String casFilterUrlPattern) {
        this.casFilterUrlPattern = casFilterUrlPattern;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public void setLoginSuccessUrl(String loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }

    public String getLoginFailUrl() {
        return loginFailUrl;
    }

    public void setLoginFailUrl(String loginFailUrl) {
        this.loginFailUrl = loginFailUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getCasServerLogoutUrl() {
        return casServerLogoutUrl;
    }

    public void setCasServerLogoutUrl(String casServerLogoutUrl) {
        this.casServerLogoutUrl = casServerLogoutUrl;
    }

    public String getLocalServerLoginUrl() {
        return localServerLoginUrl;
    }

    public void setLocalServerLoginUrl(String localServerLoginUrl) {
        this.localServerLoginUrl = localServerLoginUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }
}
