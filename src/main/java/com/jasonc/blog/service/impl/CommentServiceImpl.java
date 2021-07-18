package com.jasonc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jasonc.blog.dto.CommentDTO;
import com.jasonc.blog.entity.Comment;
import com.jasonc.blog.mapper.CommentMapper;
import com.jasonc.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentDTO> listCommentDTOByBlogId(Integer blogId) {

        // 查询所有根评论
        List<CommentDTO> rootCommentDTOList = commentMapper.listRootCommentDTOByBlogId(blogId);

        // 查询每个根评论的所有回复评论
        for (CommentDTO rootCommentDTO : rootCommentDTOList) {
            List<CommentDTO> replyCommentDTOList = commentMapper.listReplyCommentDTO(rootCommentDTO.getId());
            rootCommentDTO.setReplyCommentDTOs(replyCommentDTOList);
            // 查询每个回复评论的父评论
            for (CommentDTO replyCommentDTO : replyCommentDTOList) {
                CommentDTO parentCommentDTO = commentMapper.getParentCommentDTO(replyCommentDTO.getParentCommentId());
                replyCommentDTO.setParentCommentDTO(parentCommentDTO);
            }
        }
        return rootCommentDTOList;
    }
}
