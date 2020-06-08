package com.liaojie.service.user;

import com.liaojie.entity.User;
import org.springframework.web.bind.annotation.RequestParam;

//import javax.persistence.Id;
import java.util.List;

public interface UserService {
    public List<User> findUsersWithConditions(User user);

    public boolean addNewUser(User user);

    /**
     * 用户登录
     *
     * @param userCode
     * @param userPassword
     * @return
     */
    public User login(String userCode, String userPassword) throws Exception;

    public int getUserCount(String queryUserName, int _queryUserRole);

    /**
     * 根据条件查询用户列表
     *
     * @param queryUserName
     * @param queryUserRole
     * @return
     */
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);

    public boolean add(User user);

    /**
     * 根据ID查找user
     *
     * @param id
     * @return
     */
    public User getUserById(Integer id);

    public boolean update(User user);

    public int delUser(Integer id);

    public int XiuPwd(@RequestParam String newpassword, Integer id);
}
