package cn.haiwai.newsnotification.service;

import cn.haiwai.newsnotification.dao.UserDao;
import cn.haiwai.newsnotification.dao.bean.RoleDO;
import cn.haiwai.newsnotification.dao.bean.UserDO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MyShiroRealm extends CasRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;



    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     *
     * @see ：本例中该方法的调用时机为需授权资源被访问时
     * @see ：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * @see ：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("————————————————————执行Shiro权限认证————————————————");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        String loginName = (String) super.getAvailablePrincipal(principalCollection);
        //到数据库查是否有此对象
        UserDO user = userDao.getUser(loginName);// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        if (user == null)
            return null;
        //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //用户的角色集合
        info.setRoles(userService.getRolesName(user));
        //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
        Set<RoleDO> roleDOS = user.getRoles();
        if (roleDOS.isEmpty())
            return info;
        for (RoleDO role : roleDOS) {
            info.addStringPermissions(role.getModulesName());
        }
        logger.info("——————————————执行Shiro权限认证完成，添加角色 资源完成——————————————————————");
        // 或者按下面这样添加
        //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
//            simpleAuthorInfo.addRole("admin");
        //添加权限
//            simpleAuthorInfo.addStringPermission("admin:manage");
//            logger.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
        //info.
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        return  super.doGetAuthenticationInfo(authenticationToken);
        //集成cas后不用执行密码登录
       /* //UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        token.setRememberMe(true);
        logger.info("验证当前Subject时获取到token为：" + token.toString());
        //查出是否有此用户
        UserBO user = userService.getUserByName(token.getUsername());
        if (user != null) {
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro CredentialsMatcher会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());
        }
        return null;*/
    }
}
