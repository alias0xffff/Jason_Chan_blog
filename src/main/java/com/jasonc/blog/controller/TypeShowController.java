package com.jasonc.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasonc.blog.constant.CommonConsts;
import com.jasonc.blog.dto.BlogSearchConditionDTO;
import com.jasonc.blog.dto.IndexBlogDTO;
import com.jasonc.blog.dto.TypeNameAndBlogCountDTO;
import com.jasonc.blog.exception.NotFoundException;
import com.jasonc.blog.service.BlogService;
import com.jasonc.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.controller
 * @ClassName: TypeController
 * @Author: Jason Chan
 * @Description:
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable("id") Integer id,
                        @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                        Model model) {
        // 获取所有type和每个type的博客条数
        List<TypeNameAndBlogCountDTO> typeNameAndBlogCountDTOList = typeService.getTopTypeNameAndBlogCountDTO(Integer.MAX_VALUE);
        if (typeNameAndBlogCountDTOList.isEmpty()) {
            throw new NotFoundException();
        } else {
            // 根据typeId查博客
            BlogSearchConditionDTO blogSearchConditionDTO = new BlogSearchConditionDTO();
            if (id == -1) {
                // 初始id为第一个type的id
                id = typeNameAndBlogCountDTOList.get(0).getId();
                blogSearchConditionDTO.setTypeId(id);
            } else {
                blogSearchConditionDTO.setTypeId(id);
            }
            IPage<IndexBlogDTO> indexBlogDTOIPage = new Page<>(pageNum, CommonConsts.PAGE_SIZE);
            IPage<IndexBlogDTO> indexBlogDTOPage = blogService.getIndexBlogDTOPageByCondition(indexBlogDTOIPage, blogSearchConditionDTO);
            model.addAttribute("types", typeNameAndBlogCountDTOList);
            model.addAttribute("pageInfo", indexBlogDTOPage);
            model.addAttribute("activeTypeId", id);
            return "types";
        }

    }
}
