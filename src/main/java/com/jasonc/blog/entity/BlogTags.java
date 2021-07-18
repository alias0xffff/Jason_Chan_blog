package com.jasonc.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@TableName("t_blog_tags")
@ApiModel(value = "BlogTags对象", description = "")
public class BlogTags extends Model<BlogTags> {

    private static final long serialVersionUID = 1L;
    // 指定主键生成策略使用雪花算法（默认策略）
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private Integer blogId;

    private Integer tagId;


    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "BlogTags{" +
                "blogsId=" + blogId +
                ", tagsId=" + tagId +
                "}";
    }
}
