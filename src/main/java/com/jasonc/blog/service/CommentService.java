package com.jasonc.blog.service;

import com.jasonc.blog.dto.CommentDTO;
import com.jasonc.blog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason Chan
 * @since 2021-06-16
 */
public interface CommentService extends IService<Comment> {

    List<CommentDTO> listCommentDTOByBlogId(Integer blogId);

}
