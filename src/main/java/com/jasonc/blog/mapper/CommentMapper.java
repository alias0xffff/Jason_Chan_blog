package com.jasonc.blog.mapper;

import com.jasonc.blog.dto.CommentDTO;
import com.jasonc.blog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentDTO> listRootCommentDTOByBlogId(Integer blogId);

    List<CommentDTO> listReplyCommentDTO(Integer rootCommentId);

    CommentDTO getParentCommentDTO(Integer parentCommentId);
}
