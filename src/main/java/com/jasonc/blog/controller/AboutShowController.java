package com.jasonc.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.controller
 * @ClassName: AboutShowController
 * @Author: Jason Chan
 * @Description:
 */
@Controller
public class AboutShowController {
    @GetMapping(value = "/about")
    public String about() {
        return "about";
    }
}
