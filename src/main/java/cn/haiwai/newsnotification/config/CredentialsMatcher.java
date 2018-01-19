package cn.haiwai.newsnotification.config;

import cn.haiwai.newsnotification.manage.util.Md5;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * 自己实现一个加密算法，如果集成cas，需要在cas里也自定义加密算法
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String inPassword = new String(usernamePasswordToken.getPassword());
        String dbPassword = (String) info.getCredentials();
        return dbPassword.equals(Md5.getMd5(inPassword));


    }
}
