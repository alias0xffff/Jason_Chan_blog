package com.jasonc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasonc.blog.dto.TypeNameAndBlogCountDTO;
import com.jasonc.blog.entity.Blog;
import com.jasonc.blog.entity.BlogTags;
import com.jasonc.blog.entity.Comment;
import com.jasonc.blog.entity.Type;
import com.jasonc.blog.mapper.BlogMapper;
import com.jasonc.blog.mapper.BlogTagsMapper;
import com.jasonc.blog.mapper.CommentMapper;
import com.jasonc.blog.mapper.TypeMapper;
import com.jasonc.blog.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.bytebuddy.description.type.TypeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogTagsMapper blogTagsMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public IPage<Type> getTypePage(IPage<Type> typeIPage) {
        IPage<Type> typeList = typeMapper.selectPage(typeIPage, null);
        return typeList;
    }

    @Override
    public List<Type> getAllType() {
        List<Type> typeList = (List<Type>) redisTemplate.opsForValue().get("typeList");
        if (typeList == null) {
            typeList = typeMapper.selectList(null);
            redisTemplate.opsForValue().set("typeList", typeList, 10, TimeUnit.SECONDS);
        }
        return typeList;
    }

    @Override
    public Type getType(Integer id) {
        Type type = typeMapper.selectById(id);
        return type;
    }

    @Override
    public List<TypeNameAndBlogCountDTO> getTopTypeNameAndBlogCountDTO(Integer size) {
        List<TypeNameAndBlogCountDTO> allTypeNameAndBlogCountDTO = (List<TypeNameAndBlogCountDTO>) redisTemplate.opsForValue().get("allTypeNameAndBlogCountDTO");
        if (allTypeNameAndBlogCountDTO == null) {
            allTypeNameAndBlogCountDTO = typeMapper.getTopTypeNameAndBlogCountDTO(size);
            redisTemplate.opsForValue().set("allTypeNameAndBlogCountDTO", allTypeNameAndBlogCountDTO, 10, TimeUnit.SECONDS);
        }
        return allTypeNameAndBlogCountDTO;
    }

    @Override
    public boolean removeType(Integer id) {
        // 删除类型前需要先删除这种类型的所有博客,可能有0篇或多篇,删除博客前先解除博客和标签的关联以及其评论
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<Blog>()
                .eq(Blog::getTypeId, id);
        List<Blog> blogList = blogMapper.selectList(queryWrapper);
        blogList.forEach(blog -> {
            // 删除博客-标签关联
            blogTagsMapper.delete(new LambdaQueryWrapper<BlogTags>()
                    .eq(BlogTags::getBlogId, blog.getId()));
            // 删除评论
            commentMapper.delete(new LambdaQueryWrapper<Comment>()
                    .eq(Comment::getBlogId, blog.getId()));
        });
        blogMapper.delete(queryWrapper);
        int typeRows = typeMapper.deleteById(id);
        return typeRows == 1;
    }
}
