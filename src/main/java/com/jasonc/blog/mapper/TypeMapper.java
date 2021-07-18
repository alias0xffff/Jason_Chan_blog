package com.jasonc.blog.mapper;

import com.jasonc.blog.dto.TypeNameAndBlogCountDTO;
import com.jasonc.blog.entity.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface TypeMapper extends BaseMapper<Type> {

    List<TypeNameAndBlogCountDTO> getTopTypeNameAndBlogCountDTO(Integer size);
}
