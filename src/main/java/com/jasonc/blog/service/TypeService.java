package com.jasonc.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jasonc.blog.dto.TypeNameAndBlogCountDTO;
import com.jasonc.blog.entity.Type;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason Chan
 * @since 2021-06-16
 */
public interface TypeService extends IService<Type> {

    IPage<Type> getTypePage(IPage<Type> typeIPage);

    List<Type> getAllType();

    Type getType(Integer id);

    List<TypeNameAndBlogCountDTO> getTopTypeNameAndBlogCountDTO(Integer size);

    boolean removeType(Integer id);
}
