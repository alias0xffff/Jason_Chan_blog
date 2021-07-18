package com.jasonc.blog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasonc.blog.constant.CommonConsts;
import com.jasonc.blog.entity.Type;
import com.jasonc.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * @Program: blog
 * @Package: com.jasonc.blog.controller.admin
 * @ClassName: TypeController
 * @Author: Jason Chan
 * @Description:
 */
@Controller
@RequestMapping(value = "/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping(value = "types")
    public String typeList(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        IPage<Type> typeIPage = new Page<>(pageNum, CommonConsts.PAGE_SIZE);
        IPage<Type> typePage = typeService.getTypePage(typeIPage);
        model.addAttribute("pageInfo", typePage);
        return "admin/types";
    }

    @GetMapping(value = "types/{id}/input")
    public String toUpdateType(@PathVariable("id") Integer id, Model model) {
        Type type = typeService.getType(id);
        model.addAttribute("type", type);
        return "admin/types-input";
    }

    @GetMapping(value = "types/input")
    public String toAddType(Model model) {
        return "admin/types-input";
    }

    @PostMapping(value = "/types/{id}")
    public String updateType(Type type, RedirectAttributes redirectAttributes) {
        boolean flag = typeService.updateById(type);
        if (flag) {
            redirectAttributes.addAttribute("message", "更新成功");
        } else redirectAttributes.addAttribute("message", "跟新失败");

        return "redirect:/admin/types";
    }

    @PostMapping(value = "/types")
    public String addType(Type type, RedirectAttributes redirectAttributes) {
        boolean flag = typeService.save(type);
        if (flag) {
            redirectAttributes.addFlashAttribute("message", "操作成功");
        } else redirectAttributes.addFlashAttribute("message", "操作失败");
        return "redirect:/admin/types";
    }

    @GetMapping(value = "types/{id}/delete")
    public String deleteType(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean flag = typeService.removeType(id);
        if(flag) {
            redirectAttributes.addFlashAttribute("message", "删除成功");
        } else redirectAttributes.addFlashAttribute("message", "删除失败");
        return "redirect:/admin/types";
    }
}
