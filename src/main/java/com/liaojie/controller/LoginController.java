package com.liaojie.controller;

import com.liaojie.entity.User;
import com.liaojie.service.user.UserService;
import com.liaojie.tools.Constants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private Logger logger = Logger.getLogger(LoginController.class);
    @Resource
    private UserService userService;

    @RequestMapping(value = "/login.html")
    public String login() {
        logger.debug("UserController welcome SMBMS=======");
        return "login";
    }

    @RequestMapping(value = "/loginxiu.html")
    public String loginPaw() {
        return "pwdmodify";
    }

    @RequestMapping(value = "/dologin.html", method = RequestMethod.POST)
    public String doLogin(@RequestParam String userCode,
                          @RequestParam String userPassword, HttpServletRequest request, HttpSession session) throws Exception {
        logger.debug("doLogin============");
        User user = userService.login(userCode, userPassword);

        if (null != user) {//登陆成功
            session.setAttribute(Constants.USER_SESSION, user);


            return "redirect:/main.html";
        } else {
            //页面跳转
            throw new RuntimeException("用户名或密码不正确");
        }
    }

    @RequestMapping(value = "/main.html")
    public String main() {
        return "frame";
    }

    @RequestMapping(value = "/longinPwdxiu", method = RequestMethod.GET)
    @ResponseBody
    public boolean pwdxiu(@RequestParam String oldpassword, HttpServletRequest request, HttpSession session) {

        try {
            User result = (User) session.getAttribute(Constants.USER_SESSION);
            if (result.getUserPassword().equals(oldpassword)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping(value = "/longPwd", method = RequestMethod.POST)
    public String logingai(@RequestParam String newpassword, HttpSession session) {
        int result = 0;
        try {
            User user = (User) session.getAttribute(Constants.USER_SESSION);
            int id = user.getId();
            result = userService.XiuPwd(newpassword, id);
            if (result > 0) {
                return "redirect:/login.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "pwdmodify";
    }
}
