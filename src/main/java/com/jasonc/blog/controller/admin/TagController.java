package com.jasonc.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasonc.blog.constant.CommonConsts;
import com.jasonc.blog.entity.BlogTags;
import com.jasonc.blog.entity.Tag;
import com.jasonc.blog.service.BlogTagsService;
import com.jasonc.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.controller.admin
 * @ClassName: TagController
 * @Author: Jason Chan
 * @Description:
 */
@Controller
@RequestMapping(value = "/admin")
public class TagController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogTagsService blogTagsService;

    @GetMapping("/tags")
    public String tagList(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        IPage<Tag> tagIPage = new Page<>(pageNum, CommonConsts.PAGE_SIZE);
        IPage<Tag> tagPage = tagService.getTagPage(tagIPage);
        model.addAttribute("pageInfo", tagPage);
        return "admin/tags";
    }

    @GetMapping(value = "/tags/{id}/input")
    public String toUpdateTag(@PathVariable("id") Integer id, Model model) {
        Tag tag = tagService.getById(id);
        model.addAttribute("tag", tag);
        return "admin/tags-input";
    }

    @GetMapping(value = "tags/input")
    public String toAddTag(Model model) {
        return "admin/tags-input";
    }

    @PostMapping(value = "/tags/{id}")
    public String updateTags(Tag tag, RedirectAttributes redirectAttributes) {
        boolean flag = tagService.updateById(tag);
        if (flag) {
            redirectAttributes.addAttribute("message", "更新成功");
        } else redirectAttributes.addAttribute("message", "跟新失败");

        return "redirect:/admin/tags";
    }

    @PostMapping(value = "/tags")
    public String addTag(Tag tag, RedirectAttributes redirectAttributes) {
        boolean flag = tagService.save(tag);
        if (flag) {
            redirectAttributes.addFlashAttribute("message", "操作成功");
        } else redirectAttributes.addFlashAttribute("message", "操作失败");
        return "redirect:/admin/tags";
    }

    @GetMapping(value = "tags/{id}/delete")
    public String deleteTag(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        boolean flag = tagService.removeTag(id);
        if (flag) {
            redirectAttributes.addFlashAttribute("message", "删除成功");
        } else redirectAttributes.addFlashAttribute("message", "删除失败");
        return "redirect:/admin/tags";
    }

}
