package xangto.projects.life.api.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xangto.projects.life.api.blog.entity.BlogEntity;
import xangto.projects.life.api.blog.service.BlogService;
import xangto.projects.life.api.blog.vo.BlogInfoVO;
import xangto.projects.life.api.category.entity.CategoryEntity;
import xangto.projects.life.api.category.service.CategoryService;
import xangto.projects.life.common.OptionVO;
import xangto.projects.life.common.Result;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "文章")
@RequiredArgsConstructor
@RestController
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    private final CategoryService categoryService;

    @Operation(summary = "获取所有文章")
    @GetMapping("/list")
    public Result<List<OptionVO>> getAllBlogs() {
        List<BlogEntity> list = blogService.list();
        List<OptionVO> arrayList = new ArrayList<>();
        list.forEach(item -> {
            OptionVO vo = new OptionVO(item.getTitle(),item.getId().toString());
            arrayList.add(vo);
        });
        return Result.success(arrayList);
    }

    @Operation(summary = "获取文章详情")
    @GetMapping("/{id}")
    public Result<BlogInfoVO> getBlogById(@PathVariable Long id) {
        BlogInfoVO vo = blogService.getBlogInfo(id);
        CategoryEntity category = categoryService.getById(vo.getCategoryId());
        vo.setCategoryName(category.getName());
        // todo 将标签加入进去
        return Result.success(vo);
    }
}
