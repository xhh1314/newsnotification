package cn.haiwai.newsnotification.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.TreeSet;

import cn.haiwai.newsnotification.dao.bean.RoleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.haiwai.newsnotification.dao.UserDao;
import cn.haiwai.newsnotification.dao.bean.UserDO;
import cn.haiwai.newsnotification.manage.util.Md5;


/**
 * user 操作
 *
 * @author lh
 * @time 2017年10月25日
 */
@Service
@Scope("singleton")
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 注册逻辑
     *
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public boolean register(UserBO user) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // TODO Auto-generated method stub
        UserDO userDO = new UserDO(user);
        userDO.setPassword(Md5.getMd5(user.getPassword()));
        UserDO newUser = userDao.saveUser(userDO);
        return newUser == null ? false : true;
    }


    public UserBO verificationUser(UserBO user) {
        // TODO Auto-generated method stub
        if (user == null) {
            return null;
        }
        UserDO user1 = userDao.getUser(user.getName());
        if (user1 == null) {
            return null;
        }
        //验证密码是否符合
        if (user1.getPassword().equals(Md5.getMd5(user.getPassword()))) {
            return new UserBO(user1);
        } else {
            return null;
        }


    }


    public UserBO getUserByName(String name) {
        // TODO Auto-generated method stub
        UserDO userDO = userDao.getUser(name);
        return userDO == null ? null : new UserBO(userDO);
    }


    public boolean resetPassword(String oldPassword, String newPassword, UserBO user) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // TODO Auto-generated method stub
        user.setPassword(oldPassword);
        UserBO newUser = verificationUser(user);
        if (newUser == null)
            return false;
        newPassword = Md5.getMd5(newPassword);
        newUser.setPassword(newPassword);
        return userDao.updatePassword(new UserDO(newUser)) ? true : false;

    }

    public Set<String> getRolesName(UserDO userDO) {
        Set<String> strings = new TreeSet<>();
        Set<RoleDO> roleDOS = userDO.getRoles();
        if (roleDOS.isEmpty())
            return null;
        for (RoleDO roleDO : roleDOS) {
            strings.add(roleDO.getName());
        }
        return strings;
    }


}
