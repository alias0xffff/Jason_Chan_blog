package com.jasonc.blog.dto.admin;

import com.jasonc.blog.entity.Type;

import java.io.Serializable;
import java.util.Date;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.dto
 * @ClassName: BlogQuery
 * @Author: Jason Chan
 * @Description: 博客信息简介 用于后台管理admin/blogs展示单条博客记录 和归档页面单条博客记录信息展示
 */
public class BlogQueryDTO implements Serializable {
    //  blog表中的字段
    private Integer id;
    private String title;
    private Date updateTime;
    private boolean recommend;
    private Integer typeId;
    private boolean published;
    //  博客 原创，转载，翻译
    private String flag;
    //    type表中的字段
    private String typeName;

    public BlogQueryDTO() {
    }

    public BlogQueryDTO(Integer id, String title, Date updateTime, boolean recommend, Integer typeId, boolean published, String flag, String typeName) {
        this.id = id;
        this.title = title;
        this.updateTime = updateTime;
        this.recommend = recommend;
        this.typeId = typeId;
        this.published = published;
        this.flag = flag;
        this.typeName = typeName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "BlogQueryDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", updateTime=" + updateTime +
                ", recommend=" + recommend +
                ", typeId=" + typeId +
                ", published=" + published +
                ", flag='" + flag + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
