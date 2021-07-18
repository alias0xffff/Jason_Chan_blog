package com.jasonc.blog.mapper;

import com.jasonc.blog.dto.TagNameAndBlogCountDTO;
import com.jasonc.blog.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    List<TagNameAndBlogCountDTO> getTopTagNameAndBlogCountDTO(Integer size);
}
