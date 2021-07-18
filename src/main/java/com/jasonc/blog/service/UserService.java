package com.jasonc.blog.service;

import com.jasonc.blog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason Chan
 * @since 2021-06-16
 */
public interface UserService extends IService<User> {
    User getUser(String username, String password);
    User getUser(String username);

}
