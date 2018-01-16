package cn.haiwai.newsnotification.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;


/**
 * 自定义一个shiro拦截器，实现角色的或关系
 * filterChainDefinitionMap.put("/admin/index", "roles[admin,customer]"),如果不重写，这个配置的意思就是，该用户必须同时拥有admin 和customer角色
 * 这里重写含义，改成或的含义
 *
 * @author lh
 * @date 2018/1/16
 * @since 1.0
 */
@Component
public class CustomRolesAuthorizationFilter extends RolesAuthorizationFilter {
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }
        //若当前用户是rolesArray中的任何一个，则有权限访问
        for (int i = 0; i < rolesArray.length; i++) {
            if (subject.hasRole(rolesArray[i])) {
                return true;
            }
        }

        return false;
    }
}
