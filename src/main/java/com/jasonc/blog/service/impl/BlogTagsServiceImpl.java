package com.jasonc.blog.service.impl;

import com.jasonc.blog.entity.BlogTags;
import com.jasonc.blog.mapper.BlogTagsMapper;
import com.jasonc.blog.service.BlogTagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BlogTagsServiceImpl extends ServiceImpl<BlogTagsMapper, BlogTags> implements BlogTagsService {

}
