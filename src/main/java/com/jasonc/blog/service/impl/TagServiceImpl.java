package com.jasonc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jasonc.blog.dto.TagNameAndBlogCountDTO;
import com.jasonc.blog.entity.BlogTags;
import com.jasonc.blog.entity.Tag;
import com.jasonc.blog.mapper.BlogMapper;
import com.jasonc.blog.mapper.BlogTagsMapper;
import com.jasonc.blog.mapper.TagMapper;
import com.jasonc.blog.service.BlogTagsService;
import com.jasonc.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private BlogTagsMapper blogTagsMapper;
    @Autowired
    private BlogMapper blogMapper;


    @Override
    public IPage<Tag> getTagPage(IPage<Tag> tagIPage) {
        IPage<Tag> tagPage = tagMapper.selectPage(tagIPage, null);
        return tagPage;
    }

    @Override
    public List<Tag> getAllTag() {
        List<Tag> tagList = (List<Tag>) redisTemplate.opsForValue().get("tagList");
        if (tagList == null) {
            tagList = tagMapper.selectList(null);
            redisTemplate.opsForValue().set("tagList", tagList, 10, TimeUnit.SECONDS);
        }
        return tagList;
    }

    @Override
    public List<TagNameAndBlogCountDTO> getTopTagNameAndBlogCountDTO(Integer size) {
        List<TagNameAndBlogCountDTO> alltagNameAndBlogCountDTO = (List<TagNameAndBlogCountDTO>) redisTemplate.opsForValue().get("alltagNameAndBlogCountDTO");
        if (alltagNameAndBlogCountDTO == null) {
            alltagNameAndBlogCountDTO = tagMapper.getTopTagNameAndBlogCountDTO(size);
            redisTemplate.opsForValue().set("alltagNameAndBlogCountDTO", alltagNameAndBlogCountDTO, 10, TimeUnit.SECONDS);
        }
        return alltagNameAndBlogCountDTO;
    }

    @Override
    public boolean removeTag(Integer id) {
        Boolean tagDeleted = true;
        // 删除标签与博客的关联，然后删除标签，允许出现没有标签的博客
        blogTagsMapper.delete(new LambdaQueryWrapper<BlogTags>()
                .eq(BlogTags::getTagId, id));
        int tagRows = tagMapper.deleteById(id);
        return tagRows == 1;
    }
}
