package com.jasonc.blog.controller.admin;

import com.jasonc.blog.entity.User;
import com.jasonc.blog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.controller.admin
 * @ClassName: LoginController
 * @Author: Jason Chan
 * @Description:
 */
@Controller
@RequestMapping(value = "/admin")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public String admin() {
        return "admin/login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam() String username,
                        @RequestParam() String password,
                        RedirectAttributes redirectAttributes,
                        HttpSession httpSession) {

        Subject subject = SecurityUtils.getSubject();
        // 登录永不过期
        // subject.getSession().setTimeout(-1000L);
        // 登录用户认证
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            try {
                subject.login(usernamePasswordToken);
            } catch (UnknownAccountException e) {
                logger.error("message : {}, Exception : {}", "用户名不存在", e.getMessage());
                redirectAttributes.addFlashAttribute("message", "用户名不存在");
                return "redirect:/admin";
            } catch (IncorrectCredentialsException e) {
                logger.error("message : {}, Exception : {}", "密码错误", e.getMessage());
                redirectAttributes.addFlashAttribute("message", "密码错误");
                return "redirect:/admin";
            }
        }

        return "admin/index";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession httpSession) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.removeAttribute("user");
        httpSession.removeAttribute("user");
        subject.logout();
        return "redirect:/admin";
    }
}


