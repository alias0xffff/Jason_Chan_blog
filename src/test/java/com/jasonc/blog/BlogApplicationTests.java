package com.jasonc.blog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jasonc.blog.dto.*;
import com.jasonc.blog.dto.admin.BlogDetailDTO;
import com.jasonc.blog.dto.admin.BlogQueryDTO;
import com.jasonc.blog.entity.Blog;
import com.jasonc.blog.entity.Tag;
import com.jasonc.blog.entity.Type;
import com.jasonc.blog.service.BlogService;
import com.jasonc.blog.service.CommentService;
import com.jasonc.blog.service.TagService;
import com.jasonc.blog.service.TypeService;
import com.jasonc.blog.util.MD5Util;
import com.jasonc.blog.util.MarkdownUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlogApplicationTests {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @Test
    void contextLoads() {
    }

    /**
     * @return void
     * @Author Jason Chan
     * @Description 比较MD5工具类和shiro md5加密
     * @Date 2021/6/15 23:39
     * @Param []
     **/
    @Test
    void md5() {
        String md5 = MD5Util.getMD5("19650223.CHEN>");
        SimpleHash simpleHash = new SimpleHash("MD5", "19650223.CHEN>", null, 1024);
        String ans = simpleHash.toString();
        System.out.println("ans= " + ans);
        System.out.println("md5= " + md5);
    }


    @Test
    void testTypePage() {
        IPage<Type> typeIPage = new Page<>(1, 3);
        IPage<Type> typePage = typeService.getTypePage(typeIPage);
        List<Type> typeList = typePage.getRecords();

        System.out.println("总记录条数 = " + typePage.getTotal());
        System.out.println("总页数 = " + typePage.getPages());
        System.out.println("当前第几页 = " + typePage.getCurrent());
        System.out.println("每页大小 = " + typePage.getSize());

    }


    @Test
    void testgetAllType() {
        List<Type> allType = typeService.getAllType();
        allType.forEach(type -> System.out.println(type));
    }

    @Test
    void testGetBlogDetailById() {
        BlogDetailDTO blogDetailById = blogService.getBlogDetailDTOById(35);
        List<Tag> tags = blogDetailById.getTags();

        System.out.println("=========================================");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(blogDetailById);
        System.out.println(blogDetailById.getTagIds());
        tags.forEach(tag -> System.out.println(tag));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("=========================================");

    }

    @Test
    void testGetBlogQueryPage() {
        IPage<BlogQueryDTO> blogQueryDTOIPage = new Page<>(1, 3);
        List<BlogQueryDTO> records = blogService.getBlogPage(blogQueryDTOIPage).getRecords();
        records.forEach(record -> {
            System.out.println(record);
        });
    }

    @Test
    void testDeleteBlogById() {
        boolean flag = blogService.deleteBlogById(7);
        if (flag) {
            System.out.println("delete success");
        } else {
            System.out.println("delete fail");
        }

    }

    @Test
    void testSearchBlogPageByCondition() {
        IPage<BlogQueryDTO> blogQueryDTOIPage = new Page<>(1, 4);
        BlogSearchConditionDTO blogSearchConditionDTO = new BlogSearchConditionDTO();
        blogSearchConditionDTO.setPublished(true);
        blogSearchConditionDTO.setRecommend(true);
        IPage<BlogQueryDTO> blogQueryDTOIPage1 = blogService.searchBlogByCondition(blogSearchConditionDTO, blogQueryDTOIPage);
        List<BlogQueryDTO> records = blogQueryDTOIPage1.getRecords();
        records.forEach(record -> System.out.println(record));
    }

    @Test
    public void testGetIndexBlogDTOPage() {
        IPage<IndexBlogDTO> indexBlogDTOIPage = new Page<>(1, 4);
        IPage<IndexBlogDTO> indexBlogDTOPage = blogService.getIndexBlogDTOPageByCondition(indexBlogDTOIPage, null);
        List<IndexBlogDTO> records = indexBlogDTOPage.getRecords();
        records.forEach(record -> System.out.println(record));
    }


    @Test
    public void testGetAllRecommendBlogDTO() {
        List<RecommendBlogDTO> allRecommendBlogDTO = blogService.getTopRecommendBlogDTO(4);
        allRecommendBlogDTO.forEach(dto -> System.out.println(dto));
    }

    @Test
    public void test() {
        List<ArchiveBlogDTO> archiveBlogDTOList = blogService.getArchiveBlogDTOList();
        archiveBlogDTOList.forEach(archiveBlogDTO -> System.out.println(archiveBlogDTO));

    }

    @Test
    public void testListCommentDTOByBlogId() {
        List<CommentDTO> commentDTOList = commentService.listCommentDTOByBlogId(2);
        commentDTOList.forEach(commentDTO -> System.out.println(commentDTO));
    }

    @Test
    public void testMarkdownUtil() {
        BlogDetailDTO blogDetailDTOAndConvertToMarkdownById = blogService.getBlogDetailDTOAndConvertToMarkdownById(35);

        System.out.println(blogDetailDTOAndConvertToMarkdownById);

    }

    @Test
    public void testlatest3Blog() {
        List<Blog> blogList = blogService.query()
                .eq("published", true)
                .orderByDesc( "update_time")
                .last("limit 3")
                .list();
        // List<Blog> blogList = blogService.listNewBlogs(3);
        blogList.forEach(blog -> System.out.println(blog.getTitle() + "   "));
    }

}
