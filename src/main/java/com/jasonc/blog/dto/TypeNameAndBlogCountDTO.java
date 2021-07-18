package com.jasonc.blog.dto;

import java.io.Serializable;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.dto
 * @ClassName: TypeNameAndBlogCountDTO
 * @Author: Jason Chan
 * @Description: 统计每个分类博客数量
 */
public class TypeNameAndBlogCountDTO implements Serializable {

    private Integer id;

    private String typeName;
//    博客数量
    private Integer blogCount;

    public TypeNameAndBlogCountDTO() {
    }

    public TypeNameAndBlogCountDTO(String typeName, Integer blogCount, Integer id) {
        this.typeName = typeName;
        this.blogCount = blogCount;
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TypeNameAndBlogCountDTO{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", blogCount=" + blogCount +
                '}';
    }
}
