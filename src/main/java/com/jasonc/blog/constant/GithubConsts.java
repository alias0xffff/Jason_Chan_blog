package com.jasonc.blog.constant;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.constant
 * @ClassName: GithubConsts
 * @Author: Jason Chan
 * @CreateTime: 2021/7/17 17:11
 * @Description: github第三方登录需要的常量
 */
public class GithubConsts {
    // 这里填写在GitHub上注册应用时候获得 CLIENT ID
    public static final String CLIENT_ID = "f09697ff548b22e93024";
    // 这里填写在GitHub上注册应用时候获得 CLIENT SECRET
    public static final String CLIENT_SECRET = "059cd4c58b63a3a9d145713a087bbefcfa5359af";
    // 回调路径
    public static final String CALLBACK = "http://localhost:8080/github/callback";
    // 获取authorization code的url
    public static final String CODE_URL = "http://https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&state=STATE&redirect_uri=" + CALLBACK + "";
    // 获取token的url
    public static final String TOKEN_URL = "https://github.com/login/oauth/access_token?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=CODE&redirect_uri=" + CALLBACK + "";
    // 获取用户信息的url
    public static final String USER_INFO_URL = "https://api.github.com/user?access_token=TOKEN";
 }
