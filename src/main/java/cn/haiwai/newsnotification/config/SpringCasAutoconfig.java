package cn.haiwai.newsnotification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class SpringCasAutoconfig {

    private String validateFilters;
    private String signOutFilters;
    private String authFilters;
    private String assertionFilters;
    private String requestWrapperFilters;
    /**
     * https://sso.lihao.com:8443/cas
     */
    @Value("${cas.casServerUrlPrefix}")
    private String casServerUrlPrefix;
    /**
     * https://sso.lihao.com:8443/cas/login
     */
    @Value("${cas.casServerLoginUrl}")
    private String casServerLoginUrl;
    /**
     *https://sso.lihao.com:8443/cas/logout
     */
    @Value("${cas.casServerLogoutUrl}")
    private String casServerLogoutUrl;
    /**
     * http://localhost:8080
     */
    @Value("${cas.localServerUrlPrefix}")
    private String localServerUrlPrefix;
    /**
     * /shiro-cas
     */
    @Value("${cas.casFilterUrlPattern}")
    private String casFilterUrlPattern;
    @Value("${cas.serverName}")
    private String serverName;
    @Value("${cas.useSession}")
    private boolean useSession = true;
    @Value("${cas.redirectAfterValidation}")
    private boolean redirectAfterValidation = true;
    @Value("${cas.localServerLoginUrl}")
    private String localServerLoginUrl;

    private String loginUrl=casServerLoginUrl+"?service="+localServerUrlPrefix+casFilterUrlPattern;

    private String logoutUrl=casServerLogoutUrl+"?service="+localServerLoginUrl;

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

    public boolean isUseSession() {
        return useSession;
    }

    public void setUseSession(boolean useSession) {
        this.useSession = useSession;
    }

    public boolean isRedirectAfterValidation() {
        return redirectAfterValidation;
    }

    public void setRedirectAfterValidation(boolean redirectAfterValidation) {
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
