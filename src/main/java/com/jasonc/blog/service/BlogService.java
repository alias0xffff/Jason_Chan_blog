package com.jasonc.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jasonc.blog.dto.*;
import com.jasonc.blog.dto.admin.BlogDetailDTO;
import com.jasonc.blog.dto.admin.BlogQueryDTO;
import com.jasonc.blog.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason Chan
 * @since 2021-06-16
 */
public interface BlogService extends IService<Blog> {


    IPage<BlogQueryDTO> getBlogPage(IPage<BlogQueryDTO> blogIPage);

    BlogDetailDTO getBlogDetailDTOById(int blogId);

    boolean updateBlog(BlogDetailDTO blogDetailDTO);

    boolean saveBlog(BlogDetailDTO blogDetailDTO);

    boolean deleteBlogById(Integer blogId);

    IPage<BlogQueryDTO> searchBlogByCondition(BlogSearchConditionDTO blogSearchConditionDTO, IPage<BlogQueryDTO> blogQueryDTOIPage);

    IPage<IndexBlogDTO> getIndexBlogDTOPageByCondition(IPage<IndexBlogDTO> indexBlogDTOIPage, BlogSearchConditionDTO blogSearchConditionDTO);

    List<RecommendBlogDTO> getTopRecommendBlogDTO(Integer size);

    List<ArchiveBlogDTO> getArchiveBlogDTOList();

    List<Blog> listNewBlogs(Integer size);

    BlogDetailDTO getBlogDetailDTOAndConvertToMarkdownById(Integer blogId);
}
