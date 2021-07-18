package com.jasonc.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.controller
 * @ClassName: QQLoginController
 * @Author: Jason Chan
 * @CreateTime: 2021/7/17 12:47
 * @Description: 用户qq登录
 */
@Controller
public class QQLoginController {
    @GetMapping(value = "/qq/oauth")
    @ResponseBody
    public String qqLogin(){
        return "/qq/oauth";
    }
}
