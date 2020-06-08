package com.liaojie.dao.user;

import com.liaojie.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    /**
     * 增加用户信息
     *
     * @param user
     * @return @
     */
    public int add(User user);

    public List<User> getUserList(User user);

    /**
     * 通过userCode获取User
     *
     * @param userCode
     * @return
     * @throws Exception
     */
    public User getLoginUser(@Param("userCode") String userCode) throws Exception;

    /**
     * 通过条件查询-用户表记录数
     *
     * @param userName
     * @param userRole
     * @return @
     */
    public int getUserCount(@Param("userName") String userName, @Param("userRole") int userRole);

    /**
     * 通过条件查询-userList
     *
     * @param userName
     * @param userRole
     * @return @
     */
    public List<User> getUserList(@Param("userName") String userName,
                                  @Param("userRole") int userRole,
                                  @Param("currentPageNo") int currentPageNo,
                                  @Param("pageSize") int pageSize);

    public User getUserById(Integer id);

    public int update(User user);

    public int del(Integer id);

    public int pwd(@Param("password") String newpassword, @Param("id") Integer id);
}
