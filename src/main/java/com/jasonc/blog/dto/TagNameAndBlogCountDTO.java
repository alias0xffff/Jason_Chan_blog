package com.jasonc.blog.dto;

import io.swagger.models.auth.In;

import java.io.Serializable;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.dto
 * @ClassName: TagNameAndBlogCountDTO
 * @Author: Jason Chan
 * @Description: 统计每个标签博客数量
 */
public class TagNameAndBlogCountDTO implements Serializable {

    private Integer id;
    private String tagName;
    private Integer blogCount;

    public TagNameAndBlogCountDTO() {
    }

    public TagNameAndBlogCountDTO(Integer id, String tagName, Integer blogCount) {
        this.id = id;
        this.tagName = tagName;
        this.blogCount = blogCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    @Override
    public String toString() {
        return "TagNameAndBlogCountDTO{" +
                "id='" + id + '\'' +
                ", tagName='" + tagName + '\'' +
                ", blogCount=" + blogCount +
                '}';
    }
}
