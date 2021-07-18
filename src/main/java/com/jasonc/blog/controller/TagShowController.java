package com.jasonc.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasonc.blog.constant.CommonConsts;
import com.jasonc.blog.dto.BlogSearchConditionDTO;
import com.jasonc.blog.dto.IndexBlogDTO;
import com.jasonc.blog.dto.TagNameAndBlogCountDTO;
import com.jasonc.blog.dto.TypeNameAndBlogCountDTO;
import com.jasonc.blog.entity.Tag;
import com.jasonc.blog.exception.NotFoundException;
import com.jasonc.blog.service.BlogService;
import com.jasonc.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.controller
 * @ClassName: TagShowController
 * @Author: Jason Chan
 * @Description:
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping(value = "/tags/{id}")
    public String tags(Model model,
                       @PathVariable("id") Integer id,
                       @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        // 获取所有tag和每个type的博客条数
        List<TagNameAndBlogCountDTO> tagNameAndBlogCountDTOList = tagService.getTopTagNameAndBlogCountDTO(Integer.MAX_VALUE);
        if (tagNameAndBlogCountDTOList.isEmpty()){
            throw new NotFoundException();
        }
        // 根据tagId查博客
        BlogSearchConditionDTO blogSearchConditionDTO = new BlogSearchConditionDTO();
        if (id == -1) {
            // 初始id为第一个type的id
            id = tagNameAndBlogCountDTOList.get(0).getId();
            blogSearchConditionDTO.setTagId(id);
        } else {
            blogSearchConditionDTO.setTagId(id);
        }
        IPage<IndexBlogDTO> indexBlogDTOIPage = new Page<>(pageNum, CommonConsts.PAGE_SIZE);
        IPage<IndexBlogDTO> indexBlogDTOPage = blogService.getIndexBlogDTOPageByCondition(indexBlogDTOIPage, blogSearchConditionDTO);
        model.addAttribute("tags", tagNameAndBlogCountDTOList);
        model.addAttribute("pageInfo", indexBlogDTOPage);
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
