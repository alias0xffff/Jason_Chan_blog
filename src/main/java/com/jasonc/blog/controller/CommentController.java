package com.jasonc.blog.controller;

import com.jasonc.blog.dto.CommentDTO;
import com.jasonc.blog.entity.Comment;
import com.jasonc.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.controller
 * @ClassName: CommentController
 * @Author: Jason Chan
 * @Description: 评论类型adminComment，0为游客评论，1为访客评论，2为管理员评论
 */
@Controller
public class CommentController {

    @Value("${comment.avatar}")
    private String avatar;

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/comments")
    public String postComments(CommentDTO commentDTO, HttpSession session) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        String loginStatus = (String) session.getAttribute("loginStatus");
        if(loginStatus != null) {
            String avatar = (String) session.getAttribute("avatar");
            // qq信息
            String openid = (String) session.getAttribute("openid");
            comment.setAvatar(avatar);
            comment.setAdminComment(1);
            // 管理员登录账号
            if("adminOpenid".equals(openid)){
                comment.setAdminComment(2);
            }
        } else {
            comment.setAvatar(avatar);
            comment.setAdminComment(0);
        }
        // 此功能前端已实现
        // 如当前评论有父评论，即当前评论为回复评论，则需要查询父评论确定当前评论的根评论，根评论为父评论或父评论的根评论
        // if(comment.getParentCommentId() != -1) {
        //     Integer rootCommentId = commentService.getRootCommentIdByParentCommentId(comment.getParentCommentId());
        //     comment.setRootCommentId(rootCommentId);
        // } else comment.setRootCommentId(-1);    // 当前评论为根评论

        commentService.save(comment);
        return "redirect:/comments/" + comment.getBlogId();
    }

    @GetMapping(value = "/comments/{blogId}")
    public String comments(@PathVariable("blogId") Integer blogid, Model model){
        List<CommentDTO> commentDTOList = commentService.listCommentDTOByBlogId(blogid);
        model.addAttribute("comments", commentDTOList);
        return "blog :: commentList";
    }
}
