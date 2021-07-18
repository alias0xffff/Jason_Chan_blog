package com.jasonc.blog.dto.admin;

import com.jasonc.blog.entity.Tag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.dto
 * @ClassName: BlogUpdate
 * @Author: Jason Chan
 * @Description: 后台管理新增和更新博客详情
 */
public class BlogDetailDTO implements Serializable {

    private Integer id;
    // Boolean的默认值是null, 需要赋值
    //  是否开启赞赏功能
    private Boolean appreciation = false;

    //  是否开启评论
    private Boolean commentabled = false;

    //  内容
    private String content;

    //  博客描述信息
    private String description;

    //  博客首图
    private String firstPicture;

    //  博客标记
    private String flag;

    //  true：发布 false：保存为草稿
    private Boolean published = false;

    //  是否推荐
    private Boolean recommend = false;

    //  是否开启转载声明
    private Boolean shareStatement = false;

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


    private String nickname;

    private String avatar;

    private List<Tag> tags;

    private String tagIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(Boolean appreciation) {
        this.appreciation = appreciation;
    }

    public Boolean getCommentabled() {
        return commentabled;
    }

    public void setCommentabled(Boolean commentabled) {
        this.commentabled = commentabled;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Boolean getShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(Boolean shareStatement) {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    // 将TagList中的tagId 拼接成字符串  [id1,id2,id3...]
    public String getTagIdsFromTagList(List<Tag> tags) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Tag tag : tags) {
            stringBuilder.append(tag.getId()).append(",");
        }
        this.tagIds = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
        return tagIds;
    }

    @Override
    public String toString() {
        return "BlogDetailDTO{" +
                "id=" + id +
                ", appreciation=" + appreciation +
                ", commentabled=" + commentabled +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", published=" + published +
                ", recommend=" + recommend +
                ", shareStatement=" + shareStatement +
                ", title='" + title + '\'' +
                ", updateTime=" + updateTime +
                ", views=" + views +
                ", typeId=" + typeId +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", tags=" + tags +
                ", tagIds='" + tagIds + '\'' +
                '}';
    }
}
