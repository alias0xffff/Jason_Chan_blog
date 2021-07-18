package com.jasonc.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasonc.blog.constant.CommonConsts;
import com.jasonc.blog.dto.*;
import com.jasonc.blog.dto.admin.BlogDetailDTO;
import com.jasonc.blog.entity.Blog;
import com.jasonc.blog.service.BlogService;
import com.jasonc.blog.service.CommentService;
import com.jasonc.blog.service.TagService;
import com.jasonc.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.controller.admin
 * @ClassName: IndexController
 * @Author: Jason Chan
 * @Description:
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @GetMapping(value = {"/index", "/"})
    public String index(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                        Model model) {
        IPage<IndexBlogDTO> indexBlogDTOIPage = new Page<>(pageNum, CommonConsts.PAGE_SIZE);
//        无条件查询博客并分页展示
        BlogSearchConditionDTO blogSearchConditionDTO = new BlogSearchConditionDTO();
        IPage<IndexBlogDTO> indexBlogDTOPage = blogService.getIndexBlogDTOPageByCondition(indexBlogDTOIPage, blogSearchConditionDTO);
//        展示前6条type并统计每条type的博客数量
        List<TypeNameAndBlogCountDTO> typeNameAndBlogCountDTOList = typeService.getTopTypeNameAndBlogCountDTO(6);//        展示前6条类型标签并统计每条标签的博客数量
//        展示前10条tag并统计每条tag的博客数量
        List<TagNameAndBlogCountDTO> tagNameAndBlogCountDTOList = tagService.getTopTagNameAndBlogCountDTO(10);
//        推荐前8条博客
        List<RecommendBlogDTO> allRecommendBlogDTO = blogService.getTopRecommendBlogDTO(8);
        model.addAttribute("pageInfo", indexBlogDTOPage);
        model.addAttribute("tags", tagNameAndBlogCountDTOList);
        model.addAttribute("types", typeNameAndBlogCountDTOList);
        model.addAttribute("recommendBlogs", allRecommendBlogDTO);
        return "index";
    }

    @GetMapping(value = "/blog/{id}")
    public String blog(@PathVariable("id") Integer id,
                       Model model) {
        BlogDetailDTO blogDetailDTO = blogService.getBlogDetailDTOAndConvertToMarkdownById(id);
//        获取每条博客的评论
        List<CommentDTO> commentDTOList = commentService.listCommentDTOByBlogId(id);

        model.addAttribute("blog", blogDetailDTO);
        model.addAttribute("comments", commentDTOList);
        return "blog";
    }

    @PostMapping(value = "/search")
    public String search(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                         String query,
                         Model model) {
        IPage<IndexBlogDTO> indexBlogDTOIPage = new Page<>(pageNum, CommonConsts.PAGE_SIZE);
//      根据标题模糊查询
        BlogSearchConditionDTO blogSearchConditionDTO = new BlogSearchConditionDTO();
        blogSearchConditionDTO.setTitle(query);
        IPage<IndexBlogDTO> indexBlogDTOPage = blogService.getIndexBlogDTOPageByCondition(indexBlogDTOIPage, blogSearchConditionDTO);
        model.addAttribute("pageInfo", indexBlogDTOPage);
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping(value = "/footer/newblog")
    public String newBlogs(Model model) {

        List<Blog> blogList = (List<Blog>) redisTemplate.opsForValue().get("listNewBlogs");
        if (blogList == null) {
            // 使用mybatisplus service链式查询最新3条博客
            blogList = blogService.query()
                    .eq(true, "published", true)
                    .orderByDesc(true, "update_time")
                    .last("limit 3")
                    .list();
            redisTemplate.opsForValue().set("listNewBlogs", blogList, 10, TimeUnit.SECONDS);
        }
        // List<Blog> blogList = blogService.listNewBlogs(3);
        model.addAttribute("newblogs", blogList);
        return "_fragments :: newblogList";
    }
}
