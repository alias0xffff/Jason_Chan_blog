package com.jasonc.blog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasonc.blog.constant.CommonConsts;
import com.jasonc.blog.dto.admin.BlogDetailDTO;
import com.jasonc.blog.dto.admin.BlogQueryDTO;
import com.jasonc.blog.dto.BlogSearchConditionDTO;
import com.jasonc.blog.entity.Tag;
import com.jasonc.blog.entity.Type;
import com.jasonc.blog.entity.User;
import com.jasonc.blog.service.BlogService;
import com.jasonc.blog.service.TagService;
import com.jasonc.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.controller.admin
 * @ClassName: BlogController
 * @Author: Jason Chan
 * @Description:
 */
@Controller
@RequestMapping(value = "/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    private void getAllTypeAndTag(Model model) {
        List<Type> allType = typeService.getAllType();
        List<Tag> allTag = tagService.getAllTag();
        model.addAttribute("tags", allTag).addAttribute("types", allType);
    }

    @GetMapping(value = "/blogs")
    public String listBLog(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        IPage<BlogQueryDTO> blogQueryIPage = new Page<>(pageNum, CommonConsts.PAGE_SIZE);
        IPage<BlogQueryDTO> blogQueriePage = blogService.getBlogPage(blogQueryIPage);
        model.addAttribute("pageInfo", blogQueriePage);
        model.addAttribute("pageNum", pageNum);
        this.getAllTypeAndTag(model);
        return "admin/blogs";
    }

    @GetMapping(value = "/blogs/input")
    public String input(Model model) {
        this.getAllTypeAndTag(model);
        return "admin/blogs-input";
    }

    @GetMapping(value = "/blogs/{id}/input")
    public String toUpdate(Model model, @PathVariable("id") Integer blogId) {
        BlogDetailDTO blogDetailDTO = blogService.getBlogDetailDTOById(blogId);
        this.getAllTypeAndTag(model);
        model.addAttribute("blog", blogDetailDTO);
        return "admin/blogs-input";
    }


    @PostMapping(value = "/blogs")
    public String update(Model model, BlogDetailDTO blogDetailDTO, RedirectAttributes redirectAttributes, HttpSession session) {

        boolean flag = true;

        User user = (User) session.getAttribute("user");
        blogDetailDTO.setUserId(user.getId());

        Integer blogId = blogDetailDTO.getId();

//       如果数据库中有博客记录，则更新，否则新增
        if (blogId == null) {
            flag = blogService.saveBlog(blogDetailDTO);
        } else {
            flag = blogService.updateBlog(blogDetailDTO);
        }
        if (flag) {
            redirectAttributes.addFlashAttribute("message", "操作成功");
        } else {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping(value = "blogs/{id}/delete")
    public String deleteBlog(@PathVariable("id") Integer blogId, RedirectAttributes redirectAttributes) {
        boolean flag = blogService.deleteBlogById(blogId);
        if (flag) {
            redirectAttributes.addFlashAttribute("message", "删除成功");
        }
        return "redirect:/admin/blogs";
    }
/**
 * @Author Jason Chan
 * @Description 按条件查询博客
 * @Param [pageNum, blogSearchConditionDTO, model]
 * @return java.lang.String
 **/
    @PostMapping(value = "/blogs/search")
    public String search(Integer pageNum,
                         BlogSearchConditionDTO blogSearchConditionDTO,
                         Model model) {
        IPage<BlogQueryDTO> blogQueryDTOIPage = new Page<>(pageNum, CommonConsts.PAGE_SIZE);
        IPage<BlogQueryDTO> blogQueryPage = blogService.searchBlogByCondition(blogSearchConditionDTO, blogQueryDTOIPage);
        if (blogQueryPage != null) {
            model.addAttribute("message", "操作成功");
        }
        model.addAttribute("pageInfo", blogQueryPage);
        model.addAttribute("pageNum", pageNum);
        this.getAllTypeAndTag(model);
        return "admin/blogs :: blogList";
    }



}
