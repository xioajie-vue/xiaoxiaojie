package com.liaojie.controller;

import com.alibaba.fastjson.JSON;
import com.liaojie.entity.Role;
import com.liaojie.entity.User;
import com.liaojie.service.Role.RoleService;
import com.liaojie.service.user.UserService;
import com.liaojie.tools.Constants;
import com.liaojie.tools.PageSupport;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    /**
     * 显示用户管理页面
     *
     * @param model
     * @param queryUserName
     * @param queryUserRole
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "/list.html")
    public String getUserList(Model model,
                              @RequestParam(value = "queryname", required = false) String queryUserName,
                              @RequestParam(value = "queryUserRole", required = false) String queryUserRole,
                              @RequestParam(value = "pageIndex", required = false) String pageIndex) {
        int _queryUserRole = 0;
        List<User> userList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;

        if (queryUserName == null) {
            queryUserName = "";
        }
        if (queryUserRole != null && !queryUserRole.equals("")) {
            _queryUserRole = Integer.parseInt(queryUserRole);
        }

        if (pageIndex != null) {
            currentPageNo = Integer.valueOf(pageIndex);
        }

        //总数量（表）
        int totalCount = userService.getUserCount(queryUserName, _queryUserRole);
        //总页数
        PageSupport pages = new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        int totalPageCount = pages.getTotalPageCount();

        //控制首页和尾页
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }
        userList = userService.getUserList(queryUserName, _queryUserRole, currentPageNo, pageSize);
        model.addAttribute("userList", userList);
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", queryUserRole);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);

        return "userlist";
    }

    @RequestMapping(value = "/add.html")
    public String addUser() {
        return "useradd";
    }

    @RequestMapping(value = "/addsave.html", method = RequestMethod.POST)
    public String addUserSave(User user, HttpServletRequest request, HttpSession session) {
        User loginUser = (User) session.getAttribute(Constants.USER_SESSION);
        user.setCreatedBy(loginUser.getId());
        user.setCreationDate(new Date());
        if (userService.add(user)) {
            return "redirect:/user/list.html";
        }
        return "useradd";
    }

    @RequestMapping(value = "/update.html")
    public String updateUser(Integer uid, Model model) {

        User user = userService.getUserById(uid);

        model.addAttribute(user);
        return "usermodify";
    }

    @RequestMapping(value = "/updatesave.html", method = RequestMethod.POST)
    public String addUserSave(User user, HttpSession session) {
        User loginUser = (User) session.getAttribute(Constants.USER_SESSION);

        user.setModifyBy(loginUser.getId());
        user.setModifyDate(new Date());

        boolean result = userService.update(user);

        if (result) {
            return "redirect:/user/list.html";
        }
        return "usermodify";
    }


    @RequestMapping(value = "/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        model.addAttribute(userService.getUserById(id));
        return "userview";
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @ResponseBody
    public User view(@RequestParam Integer id) {
        User user = new User();
        user = userService.getUserById(id);
        return user;
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    @ResponseBody
    public int delUser(@RequestParam Integer id) {
        int result = 0;
        result = userService.delUser(id);
        return result;
    }

}
