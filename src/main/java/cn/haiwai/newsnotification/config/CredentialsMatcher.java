package cn.haiwai.newsnotification.config;

import cn.haiwai.newsnotification.manage.util.Md5;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class CredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken) token;
        String inPassword=new String(usernamePasswordToken.getPassword());
        String dbPassword=(String) info.getCredentials();
        try {
            return dbPassword.equals(Md5.getMd5(inPassword));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  false;

    }
}
