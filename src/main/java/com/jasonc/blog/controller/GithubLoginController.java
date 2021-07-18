package com.jasonc.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jasonc.blog.constant.GithubConsts;
import com.jasonc.blog.entity.GithubUser;
import com.jasonc.blog.service.GithubLoginService;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.controller
 * @ClassName: GithubLoginController
 * @Author: Jason Chan
 * @CreateTime: 2021/7/17 16:34
 * @Description:
 */
@Controller
public class GithubLoginController {


    @Autowired
    private GithubLoginService githubLoginService;

    @GetMapping(value = "/github/oauth")
    public String githubAuthorization(HttpSession session) throws UnsupportedEncodingException {
        //随机生成uuid用于第三方应用防止CSRF攻击
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        session.setAttribute("state", uuid);

        // 第一步，获取Authorization Code
        String url = "https://github.com/login/oauth/authorize?client_id=" + GithubConsts.CLIENT_ID +
                "&redirect_uri=" + URLEncoder.encode(GithubConsts.CALLBACK, "UTF-8") +
                "&state=" + uuid;
        return "redirect:" + url;
    }

    @RequestMapping(value = "/github/callback")
    public String githubCallback(Model model,
                                 String code,
                                 String state) throws IOException {
        if (StringUtils.isNoneBlank(code, state)) {
            GithubUser githubUser = githubLoginService.githubCallback(code, state);
            model.addAttribute("githubUser", githubUser);
            // 登录成功，跳转至资料页面
            return "githubInfo";
        }
        // 登录失败,返回首页
        return "redirect:/index";
    }

    @GetMapping(value = "/githubInfo")
    public String githubInfo(HttpSession session, Model model) {
        // 查询当前登录用户详细信息，如果登录信息已过期，则重新登录
        String node_id = (String) session.getAttribute("node_id");
        if (node_id == null) {
            return "redirect:/github/oauth";
        }
        GithubUser githubUser = githubLoginService.getOne(new LambdaQueryWrapper<GithubUser>()
                .eq(GithubUser::getNodeId, node_id));
        if (githubUser == null) {
            return "redirect:/github/oauth";
        }
        model.addAttribute("github", githubUser);
        return "githubInfo";
    }

    @PostMapping(value = "/saveIp")
    public String saveIp(HttpSession session, GithubUser githubUser) {
        Object node_id = session.getAttribute("node_id");
        GithubUser user = githubLoginService.getOne(new LambdaQueryWrapper<GithubUser>()
                .eq(GithubUser::getNodeId, node_id));
        user.setCip(githubUser.getCip());
        user.setCid(githubUser.getCid());
        user.setCname(githubUser.getCname());
        githubLoginService.update(user, new LambdaUpdateWrapper<GithubUser>()
                .eq(GithubUser::getNodeId, node_id));
        return "githubInfo";
    }

    @GetMapping(value = "/githublogout")
    public String githubLogout(HttpSession session) {
        session.removeAttribute("loginStatus");
        session.removeAttribute("avatar");
        session.removeAttribute("nickname");
        session.removeAttribute("node_id");
        session.removeAttribute("loginType");
        return "redirect:/index";
    }

}
