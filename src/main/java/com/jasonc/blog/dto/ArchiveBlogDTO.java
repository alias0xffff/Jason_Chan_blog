package com.jasonc.blog.dto;

import com.jasonc.blog.dto.admin.BlogQueryDTO;

import java.io.Serializable;
import java.util.List;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.dto
 * @ClassName: ArchiveBlog
 * @Author: Jason Chan
 * @Description: 展示归档页面
 */
public class ArchiveBlogDTO implements Serializable {
//    博客发布年份
    private String year;

    private List<BlogQueryDTO> blogQueryDTOList;

    public ArchiveBlogDTO() {
    }

    public ArchiveBlogDTO(String year, List<BlogQueryDTO> blogQueryDTOList) {
        this.year = year;
        this.blogQueryDTOList = blogQueryDTOList;
    }

    public String getYear() {
        return year;
    }


    public void setYear(String year) {
        this.year = year;
    }

    public List<BlogQueryDTO> getBlogQueryDTOList() {
        return blogQueryDTOList;
    }

    public void setBlogQueryDTOList(List<BlogQueryDTO> blogQueryDTOList) {
        this.blogQueryDTOList = blogQueryDTOList;
    }

    @Override
    public String toString() {
        return "ArchiveBlog{" +
                "year='" + year + '\'' +
                ", blogQueryDTOList=" + blogQueryDTOList +
                '}';
    }
}
