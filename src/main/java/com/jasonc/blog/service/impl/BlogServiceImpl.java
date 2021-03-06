package com.jasonc.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jasonc.blog.dto.*;
import com.jasonc.blog.dto.admin.BlogDetailDTO;
import com.jasonc.blog.dto.admin.BlogQueryDTO;
import com.jasonc.blog.entity.Blog;
import com.jasonc.blog.entity.BlogTags;
import com.jasonc.blog.entity.Tag;
import com.jasonc.blog.entity.User;
import com.jasonc.blog.exception.NotFoundException;
import com.jasonc.blog.mapper.*;
import com.jasonc.blog.service.BlogService;
import com.jasonc.blog.util.MarkdownUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagsMapper blogTagsMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public IPage<BlogQueryDTO> getBlogPage(IPage<BlogQueryDTO> blogIpage) {
        IPage<BlogQueryDTO> allBlogQueryPage = blogMapper.getBlogQueryPage(blogIpage);
        return allBlogQueryPage;

    }

    @Override
    public BlogDetailDTO getBlogDetailDTOById(int blogId) {
        Blog blog = (Blog) redisTemplate.opsForHash().get("blog", JSON.toJSON(blogId));
        if (blog == null) {
            blog = blogMapper.selectById(blogId);
            if (blog == null) {
                throw new NotFoundException("??????????????????");
            }
            redisTemplate.opsForHash().put("blog", JSON.toJSON(blogId), blog);
            redisTemplate.expire("blog", 10, TimeUnit.SECONDS);
        }

        User user = userMapper.selectById(blog.getUserId());

        BlogDetailDTO blogDetailDTO = new BlogDetailDTO();
        // ??????blog user dto ????????????id????????????????????????id, ??????dto???id??????user ???id ??????
        BeanUtils.copyProperties(blog, blogDetailDTO, "id");
        BeanUtils.copyProperties(user, blogDetailDTO, "id");
        List<Tag> tags = blogMapper.getTags(blog.getId());
        if (!tags.isEmpty() && tags != null) {
            blogDetailDTO.setTagIds(blogDetailDTO.getTagIdsFromTagList(tags));
            blogDetailDTO.setTags(tags);
        }
        blogDetailDTO.setId(blog.getId());
        return blogDetailDTO;
    }

    // ???????????????????????????????????????
    @Override
    public boolean updateBlog(BlogDetailDTO blogDetailDTO) {

        Blog blog = new Blog();
        BeanUtils.copyProperties(blogDetailDTO, blog);
        blog.setUpdateTime(new Date());
        int rows = blogMapper.updateById(blog);
        String tagIds = blogDetailDTO.getTagIds();

        this.updateBlogTags(blog.getId(), tagIds);
        return rows == 1;
    }

    /**
     * @return boolean
     * @Author Jason Chan
     * @Description ??????t_blog_tags???, ??????tagIds????????????,tagIds?????????null???""???????????????????????????????????????????????????????????????
     * ?????????????????????????????????????????????????????????????????????tagIDs??????????????????
     * @Param [blogId, tagIds]
     **/
    private boolean updateBlogTags(int blogId, String tagIds) {
        boolean updated = true;
        blogTagsMapper.delete(new LambdaQueryWrapper<BlogTags>()
                .eq(BlogTags::getBlogId, blogId));
        if (StringUtils.isNotBlank(tagIds)) {
            // tagIds??????null ??????????????????, ???tagIds ????????????tagId,??????????????????
            String[] tagIdList = tagIds.split(",");
            for (String tagId : tagIdList) {
                BlogTags blogTags = new BlogTags();
                blogTags.setBlogId(blogId);
                blogTags.setTagId(Integer.parseInt(tagId));
                int row = blogTagsMapper.insert(blogTags);
                if (row != 1) {
                    updated = false;
                }
            }
        }
        return updated;
    }

    @Override
    public boolean saveBlog(BlogDetailDTO blogDetailDTO) {
//      ??????????????????,????????????????????????????????????
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogDetailDTO, blog);
        blog.setUpdateTime(new Date());

        String tagIds = blogDetailDTO.getTagIds();
//      ??????????????????
        blog.setViews(0);

        int rows = blogMapper.insert(blog);
        boolean flag = this.updateBlogTags(blog.getId(), tagIds);

        return rows == 1 && flag;
    }


    @Override
    public boolean deleteBlogById(Integer blogId) {
        Map<String, Object> map = new HashMap<>();
//      ?????????????????????????????????????????? ????????????????????????????????????
        map.put("blog_id", blogId);
        int blogTagRows = blogTagsMapper.deleteByMap(map);
        int blogRows = blogMapper.deleteById(blogId);
        int commentDeleteCount = commentMapper.deleteByMap(map);
        return (blogRows == 1 && blogTagRows == 1 && commentDeleteCount > 0);
    }

    @Override
    public IPage<BlogQueryDTO> searchBlogByCondition(BlogSearchConditionDTO blogSearchConditionDTO,
                                                     IPage<BlogQueryDTO> blogQueryDTOIPage) {
        IPage<BlogQueryDTO> blogQueryPage = blogMapper.searchBlogByCondition(blogSearchConditionDTO, blogQueryDTOIPage);
        return blogQueryPage;
    }

    @Override
    public IPage<IndexBlogDTO> getIndexBlogDTOPageByCondition(IPage<IndexBlogDTO> indexBlogDTOIPage,
                                                              BlogSearchConditionDTO blogSearchConditionDTO) {
        IPage<IndexBlogDTO> indexBlogDTOPage = blogMapper.getIndexBlogDTOPageByCondition(indexBlogDTOIPage, blogSearchConditionDTO);
        List<IndexBlogDTO> records = indexBlogDTOPage.getRecords();
        records.forEach(record -> {
            List<Tag> tags = blogMapper.getTags(record.getId());
            record.setTagList(tags);
        });
        return indexBlogDTOPage;
    }


    @Override
    public List<RecommendBlogDTO> getTopRecommendBlogDTO(Integer size) {
        List<RecommendBlogDTO> allRecommendBlogDTO = (List<RecommendBlogDTO>) redisTemplate.opsForValue().get("allRecommendBlogDTO");
        if (allRecommendBlogDTO == null) {
            allRecommendBlogDTO = blogMapper.getTopRecommendBlogDTO(size);
            redisTemplate.opsForValue().set("allRecommendBlogDTO", allRecommendBlogDTO, 10, TimeUnit.SECONDS);
        }
        return allRecommendBlogDTO;
    }

    //    ?????????????????????????????????????????????????????????(??????)??????
    @Override
    public List<ArchiveBlogDTO> getArchiveBlogDTOList() {
        List<ArchiveBlogDTO> archiveBlogDTOList = new ArrayList<>();
        List<String> years = blogMapper.getGroupYear();
        for (String year : years) {
//            ????????????????????????????????????????????????????????????
            List<BlogQueryDTO> blogQueryDTOList = (List<BlogQueryDTO>) redisTemplate.opsForValue().get("blogQueryPerYear::" + year);
            if (blogQueryDTOList == null) {
                blogQueryDTOList = blogMapper.getBlogQueryPerYear(year);
                redisTemplate.opsForValue().set("blogQueryPerYear::" + year, blogQueryDTOList, 10, TimeUnit.SECONDS);
            }
            ArchiveBlogDTO archiveBlogDTO = new ArchiveBlogDTO(year, blogQueryDTOList);
            archiveBlogDTOList.add(archiveBlogDTO);
        }

        return archiveBlogDTOList;
    }

    @Override
    public List<Blog> listNewBlogs(Integer size) {
        // QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        // blogQueryWrapper.orderByDesc(true, "update_time")
        //         .last("limit " + size);
        // List<Blog> blogList = blogMapper.selectList(blogQueryWrapper);
        List<Blog> blogList = blogMapper.selectList(new LambdaQueryWrapper<Blog>()
                .orderByDesc(true, Blog::getUpdateTime)
                .last("limit " + size));
        return blogList;
    }

    @Override
    public BlogDetailDTO getBlogDetailDTOAndConvertToMarkdownById(Integer blogId) {
        Blog blog = (Blog) redisTemplate.opsForHash().get("blogMarkdown", JSON.toJSON(blogId));
        if (blog == null) {
            blog = blogMapper.selectById(blogId);
            if (blog == null) {
                throw new NotFoundException("??????????????????");
            }
            redisTemplate.opsForHash().put("blogMarkdown", JSON.toJSON(blogId), blog);
            redisTemplate.expire("blogMarkdown", 10, TimeUnit.SECONDS);
        }
        // ?????????????????????
        Integer views = blog.getViews();
        blog.setViews(++views);
        blogMapper.updateById(blog);


        User user = userMapper.selectById(blog.getUserId());

        BlogDetailDTO blogDetailDTO = new BlogDetailDTO();
        // ??????blog user dto ????????????id????????????????????????id, ??????dto???id??????user ???id ??????
        BeanUtils.copyProperties(blog, blogDetailDTO, "id", "content");
        BeanUtils.copyProperties(user, blogDetailDTO, "id");

        // ???????????????????????????????????????html markdown???????????????
        blogDetailDTO.setContent(MarkdownUtil.markdownToHtmlExtensions(blog.getContent()));

        List<Tag> tags = blogMapper.getTags(blog.getId());
        if (!tags.isEmpty() && tags != null) {
            blogDetailDTO.setTagIds(blogDetailDTO.getTagIdsFromTagList(tags));
            blogDetailDTO.setTags(tags);
        }
        blogDetailDTO.setId(blog.getId());
        return blogDetailDTO;
    }


}
