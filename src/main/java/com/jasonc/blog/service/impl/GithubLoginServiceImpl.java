package com.jasonc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jasonc.blog.enums.LoginTypeEnum;
import com.jasonc.blog.constant.GithubConsts;
import com.jasonc.blog.entity.GithubUser;
import com.jasonc.blog.mapper.GithubuserMapper;
import com.jasonc.blog.service.GithubLoginService;
import com.jasonc.blog.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.service.impl
 * @ClassName: GithubLoginServiceImpl
 * @Author: Jason Chan
 * @CreateTime: 2021/7/17 20:46
 * @Description:
 */
@Service
public class GithubLoginServiceImpl extends ServiceImpl<GithubuserMapper, GithubUser> implements GithubLoginService {

    @Value(value = "${cookie.time}")
    private Integer cookieTime;

    @Autowired
    private GithubuserMapper githubuserMapper;

    @Override
    public GithubUser githubCallback(String code, String state) throws IOException {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();

        //关闭浏览器后还能保持登录状态
        HttpSession session = request.getSession();
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setPath(request.getContextPath() + "/");
        // 设置cookie的生命周期
        cookie.setMaxAge(cookieTime);
        response.addCookie(cookie);
        // 拿到授权码后通过tokenUrl请求token
        String tokenUrl = GithubConsts.TOKEN_URL.replace("CODE", code);
        // 执行get请求并返回结果responseStr
        String responseStr = HttpClientUtil.doGet(tokenUrl);
        // 将返回结果由字符串解析放到map中
        Map<String, String> responseTokenUrlMap = HttpClientUtil.getMap(responseStr);
        // 从响应map中获取accessToken
        String accessToken = responseTokenUrlMap.get("access_token");

        // 通过token发送请求获取登录用户信息
        String userInfoUrl = GithubConsts.USER_INFO_URL.replace("TOKEN", accessToken);
        // 执行get请求并返回字符串结果
        responseStr = HttpClientUtil.doGet(userInfoUrl);
        // 将返回结果由字符串解析放到map中
        Map<String, String> responseUserInfoUrlMap = HttpClientUtil.getMapByJson(responseStr);
        // node_id用来唯一标识用户
        session.setAttribute("node_id", responseUserInfoUrlMap.get("node_id"));
        // github昵称
        session.setAttribute("nickname", responseUserInfoUrlMap.get("login"));
        // github头像url
        session.setAttribute("avatar", responseUserInfoUrlMap.get("avatar_url"));
        // 处于登录状态
        session.setAttribute("loginStatus", "ture");
        // 登录类型
        session.setAttribute("loginType", LoginTypeEnum.GITHUB);

        // 从数据库获取用户信息，若用户第一次登录则存入数据库中
        GithubUser githubUser = githubuserMapper.selectOne(new LambdaQueryWrapper<GithubUser>()
                .eq(GithubUser::getNodeId, responseUserInfoUrlMap.get("node_id")));
        if (githubUser == null) {
            // 用户第一次登录，将用户信息存入数据库中
            githubUser = new GithubUser();
            githubUser.setAvatar(responseUserInfoUrlMap.get("avatar_url"));
            githubUser.setCreatedTime(responseUserInfoUrlMap.get("created_at"));
            githubUser.setNickname(responseUserInfoUrlMap.get("login"));
            githubUser.setIndexUrl(responseUserInfoUrlMap.get("html_url"));
            githubUser.setNodeId(responseUserInfoUrlMap.get("node_id"));
            githubUser.setPublicRepos(responseUserInfoUrlMap.get("public_repos"));
            githubUser.setNickname(responseUserInfoUrlMap.get("login"));
            githubUser.setSubscriptions(responseUserInfoUrlMap.get("subscriptions_url"));
            githubUser.setUpdatedTime(responseUserInfoUrlMap.get("updated_at"));
            githubUser.setGithubId(responseUserInfoUrlMap.get("id"));
            githubUser.setReceivedEventsUrl(responseUserInfoUrlMap.get("received_events_url"));
            githubUser.setLoginTime(new Date().toString());
            githubuserMapper.insert(githubUser);
        }
        return githubUser;
    }
}