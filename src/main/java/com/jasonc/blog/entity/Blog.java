package com.jasonc.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@TableName("t_blog")
@ApiModel(value="Blog对象", description="")
public class Blog extends Model<Blog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//  是否开启赞赏功能
    private boolean appreciation ;

//  是否开启评论
    private boolean commentabled;

//  内容
    private String content;

//  博客创建时间,自动填充时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

//  博客描述信息
    private String description;

//  博客首图
    private String firstPicture;

//  博客 原创，转载，翻译
    private String flag;

//  true：发布 false：保存为草稿
    private boolean published ;

//  是否推荐
    private boolean recommend;

//  是否开启转载声明
    private boolean shareStatement;

//  博客标题
    private String title;

//  博客更新时间
    private Date updateTime;

//  浏览次数
    private Integer views;

//  博客类型id
    private Integer typeId;

//  博客发布者id
    private Integer userId;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean getCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public boolean getShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Blog{" +
        "id=" + id +
        ", appreciation=" + appreciation +
        ", commentabled=" + commentabled +
        ", content=" + content +
        ", createTime=" + createTime +
        ", description=" + description +
        ", firstPicture=" + firstPicture +
        ", flag=" + flag +
        ", published=" + published +
        ", recommend=" + recommend +
        ", shareStatement=" + shareStatement +
        ", title=" + title +
        ", updateTime=" + updateTime +
        ", views=" + views +
        ", typeId=" + typeId +
        ", userId=" + userId +
        "}";
    }
}
