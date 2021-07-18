package com.jasonc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jasonc.blog.entity.GithubUser;

import java.io.IOException;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.service
 * @ClassName: GithubLoginServer
 * @Author: Jason Chan
 * @CreateTime: 2021/7/17 17:03
 * @Description:
 */
public interface GithubLoginService extends IService<GithubUser> {
    GithubUser githubCallback(String code, String state) throws IOException;
}
