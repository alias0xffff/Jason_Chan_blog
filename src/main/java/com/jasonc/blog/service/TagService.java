package com.jasonc.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jasonc.blog.dto.TagNameAndBlogCountDTO;
import com.jasonc.blog.entity.Tag;
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
public interface TagService extends IService<Tag> {

    IPage<Tag> getTagPage(IPage<Tag> tagIPage);

    List<Tag> getAllTag();

    List<TagNameAndBlogCountDTO> getTopTagNameAndBlogCountDTO(Integer size);

    boolean removeTag(Integer id);
}
