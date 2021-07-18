package com.jasonc.blog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jasonc.blog.dto.admin.BlogQueryDTO;
import com.jasonc.blog.dto.BlogSearchConditionDTO;
import com.jasonc.blog.dto.IndexBlogDTO;
import com.jasonc.blog.dto.RecommendBlogDTO;
import com.jasonc.blog.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jasonc.blog.entity.Tag;

import java.util.List;

public interface BlogMapper extends BaseMapper<Blog> {
    /**
     * @Author Jason Chan
     * @Description
     * @Param [blogQueryPage] 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.jasonc.blog.dto.BlogQuery>
     **/

    IPage<BlogQueryDTO> getBlogQueryPage(IPage<?> blogQueryPage);

    IPage<BlogQueryDTO> searchBlogByCondition(BlogSearchConditionDTO blogSearchConditionDTO, IPage<BlogQueryDTO> blogQueryDTOIPage);


    IPage<IndexBlogDTO> getIndexBlogDTOPageByCondition(IPage<IndexBlogDTO> indexBlogDTOIPage, BlogSearchConditionDTO blogSearchConditionDTO);

    List<RecommendBlogDTO> getTopRecommendBlogDTO(Integer size);

    List<Tag> getTags(Integer id);

    List<String> getGroupYear();

    List<BlogQueryDTO> getBlogQueryPerYear(String year);

}

