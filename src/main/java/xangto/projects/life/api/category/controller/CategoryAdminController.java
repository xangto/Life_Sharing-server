package xangto.projects.life.api.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xangto.projects.life.api.category.dto.CategoryCreateDTO;
import xangto.projects.life.api.category.dto.CategoryUpdateDTO;
import xangto.projects.life.api.category.service.CategoryService;
import xangto.projects.life.api.category.vo.CategoryVO;
import xangto.projects.life.common.PageDTO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.Result;

@Tag(name = "文章分类管理 admin")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class CategoryAdminController {
    private final CategoryService categoryService;

    @Operation(summary = "新建文章分类")
    @PostMapping
    public Result<?> createCategory(@Valid @RequestBody CategoryCreateDTO dto) {
        boolean flag = categoryService.createCategory(dto);
        return flag ? Result.success() : Result.error();
    }

    @Operation(summary = "文章分类列表")
    @GetMapping("/list")
    public Result<PageVO<CategoryVO>> listCategory(PageDTO dto) {
        PageVO<CategoryVO> list = categoryService.getCategoryList(dto);
        return Result.success(list);
    }

    @Operation(summary = "更新文章分类")
    @PutMapping
    public Result<?> updateCategory(@Valid @RequestBody CategoryUpdateDTO dto) {
        boolean b = categoryService.updateCategory(dto);
        return b ? Result.success() : Result.error();
    }

    @Operation(summary = "删除文章分类")
    @DeleteMapping("/{id}")
    public Result<?> deleteCategory(@PathVariable Long id) {
        boolean b = categoryService.deleteCategory(id);
        return b ? Result.success() : Result.error();
    }
}
