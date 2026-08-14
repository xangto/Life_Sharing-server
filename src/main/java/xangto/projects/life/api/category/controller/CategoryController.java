package xangto.projects.life.api.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xangto.projects.life.api.category.service.CategoryService;
import xangto.projects.life.api.category.vo.CategoryVO;
import xangto.projects.life.common.PageDTO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.Result;

@Tag(name = "文章分类管理")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "文章分类列表")
    @GetMapping("/list")
    public Result<PageVO<CategoryVO>> listCategory(@Valid PageDTO dto) {
        PageVO<CategoryVO> list = categoryService.getCategoryList(dto);
        return Result.success(list);
    }
}
