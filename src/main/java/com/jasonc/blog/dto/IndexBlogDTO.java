package com.jasonc.blog.dto;

import com.jasonc.blog.entity.Tag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.dto
 * @ClassName: IndexBlogDTO
 * @Author: Jason Chan
 * @Description: index页面单条博客记录
 */
public class IndexBlogDTO implements Serializable {


//    blog表
    private Integer id;
    //  浏览次数
    private Integer views;
    //  博客首图
    private String firstPicture;
    //  博客标题
    private String title;
    //  博客更新时间
    private Date updateTime;
    //  博客描述信息
    private String description;

//    type表
    private String typeName;

    private Integer typeId;

//    user表
    private String nickName;
    private String avatar;

    private List<Tag> tagList;



    public IndexBlogDTO() {
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "IndexBlogDTO{" +
                "id=" + id +
                ", views=" + views +
                ", firstPicture='" + firstPicture + '\'' +
                ", title='" + title + '\'' +
                ", updateTime=" + updateTime +
                ", description='" + description + '\'' +
                ", typeName='" + typeName + '\'' +
                ", typeId=" + typeId +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", tagList=" + tagList +
                '}';
    }
}
