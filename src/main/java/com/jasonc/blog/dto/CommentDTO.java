package com.jasonc.blog.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jasonc.blog.entity.Comment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.dto
 * @ClassName: CommentDTO
 * @Author: Jason Chan
 * @Description:
 */
public class CommentDTO implements Serializable{

    private Integer id;

    private Integer adminComment;

    private String avatar;

    private String content;

    private Date createTime;

    private String email;

    private String nickname;

    private Integer blogId;

    private Integer parentCommentId;

    private CommentDTO parentCommentDTO;

    private Integer rootCommentId;

    private List<CommentDTO> replyCommentDTOs;

    public Integer getRootCommentId() {
        return rootCommentId;
    }

    public void setRootCommentId(Integer rootCommentId) {
        this.rootCommentId = rootCommentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(Integer adminComment) {
        this.adminComment = adminComment;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public CommentDTO getParentCommentDTO() {
        return parentCommentDTO;
    }

    public void setParentCommentDTO(CommentDTO parentCommentDTO) {
        this.parentCommentDTO = parentCommentDTO;
    }

    public List<CommentDTO> getReplyCommentDTOs() {
        return replyCommentDTOs;
    }

    public void setReplyCommentDTOs(List<CommentDTO> replyCommentDTOs) {
        this.replyCommentDTOs = replyCommentDTOs;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", adminComment=" + adminComment +
                ", avatar='" + avatar + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", blogId=" + blogId +
                ", parentCommentId=" + parentCommentId +
                ", parentCommentDTO=" + parentCommentDTO +
                ", rootCommentId=" + rootCommentId +
                ", replyCommentDTOs=" + replyCommentDTOs +
                '}';
    }
}
