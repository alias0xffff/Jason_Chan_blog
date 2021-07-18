package com.jasonc.blog.dto;

import java.io.Serializable;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.dto
 * @ClassName: RecommendBlogDTO
 * @Author: Jason Chan
 * @Description: 用于展示index.html 最新推荐 处
 */
public class RecommendBlogDTO implements Serializable {


    private Integer id;
//    博客title
    private String title;



    public RecommendBlogDTO() {
    }

    public RecommendBlogDTO(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "RecommendBlogDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
