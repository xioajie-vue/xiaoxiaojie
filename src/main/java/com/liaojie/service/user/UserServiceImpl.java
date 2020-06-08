package com.liaojie.service.user;

import com.liaojie.dao.user.UserMapper;
import com.liaojie.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;//声明UserMapper接口引用

    @Override
    @Transactional(propagation = Propagation.SUPPORTS) //调用事务回滚
    public List<User> findUsersWithConditions(User user) {
        try {
            return userMapper.getUserList(user);//调用DAO方法实现查询
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean addNewUser(User user) {
        boolean result = false;
        try {
            if (userMapper.add(user) == 1) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    @Override
    public User login(String userCode, String userPassword) throws Exception {
        User user = null;
        user = userMapper.getLoginUser(userCode);
        //匹配密码
        if (null != user) {
            if (!user.getUserPassword().equals(userPassword))
                user = null;

        }
        return user;
    }

    @Override
    public int getUserCount(String queryUserName, int queryUserRole) {
        int count = 0;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        count = userMapper.getUserCount(queryUserName, queryUserRole);
        return count;
    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        int beginIndex = (currentPageNo - 1) * pageSize;
        return userMapper.getUserList(queryUserName, queryUserRole, beginIndex,
                pageSize);
    }

    @Override
    public boolean add(User user) {
        return userMapper.add(user) > 0;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public boolean update(User user) {
        return userMapper.update(user) > 0;
    }

    @Override
    public int delUser(Integer id) {
        return userMapper.del(id);
    }

    @Override
    public int XiuPwd(String newpassword, Integer id) {
        return userMapper.pwd(newpassword, id);
    }


    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
