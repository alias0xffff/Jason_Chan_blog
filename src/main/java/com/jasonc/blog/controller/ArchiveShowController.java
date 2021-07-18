package com.jasonc.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jasonc.blog.dto.ArchiveBlogDTO;
import com.jasonc.blog.entity.Blog;
import com.jasonc.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.controller
 * @ClassName: ArchiveShowController
 * @Author: Jason Chan
 * @Description:
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping(value = "/archives")
    public String archive(Model model) {
//     查询所有已发布的博客条数
//         QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
//         blogQueryWrapper.eq("published", true);
//         int blogCount = blogService.count(blogQueryWrapper);
        Integer blogCount = blogService.count(new LambdaQueryWrapper<Blog>()
                .eq(Blog::getPublished, true));
        List<ArchiveBlogDTO> archiveBlogDTOList = blogService.getArchiveBlogDTOList();
        model.addAttribute("archiveBlogList", archiveBlogDTOList);
        model.addAttribute("blogCount", blogCount);
        return "archives";
    }

}
