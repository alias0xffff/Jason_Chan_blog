package com.jasonc.blog.dto;

import java.io.Serializable;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.dto
 * @ClassName: BlogSearchConditionDTO
 * @Author: Jason Chan
 * @Description: 根据条件查询博客
 */
public class BlogSearchConditionDTO implements Serializable {
    //  博客标题，"" 表示不输入标题作为查询条件， "abc" 表示查询标题为abc的博客
    private String title = "";
    //  博客类型id
    private Integer typeId;
    //  博客是否推荐，默认推荐
    private boolean recommend = true;
    //  博客是否发布, 默认已经发布
    private boolean published = true;
    //    根据博客标签id查询博客
    private Integer tagId;

    public BlogSearchConditionDTO() {
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    @Override
    public String toString() {
        return "BlogSearchConditionDTO{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                ", recommend=" + recommend +
                ", published=" + published +
                ", tagId='" + tagId + '\'' +
                '}';
    }
}
