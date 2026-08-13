package xangto.projects.life.api.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xangto.projects.life.api.tag.dto.TagCreateDTO;
import xangto.projects.life.api.tag.dto.TagUpdateDTO;
import xangto.projects.life.api.tag.service.TagService;
import xangto.projects.life.api.tag.vo.TagVO;
import xangto.projects.life.common.PageDTO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.Result;

@Tag(name = "标签管理 admin")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/tag")
public class TagAdminController {
    private final TagService tagService;

    @Operation(summary = "新建标签")
    @PostMapping
    public Result<?> createCategory(@Valid @RequestBody TagCreateDTO dto) {
        boolean flag = tagService.createTag(dto);
        return flag ? Result.success() : Result.error();
    }

    @Operation(summary = "标签列表")
    @GetMapping("/list")
    public Result<PageVO<TagVO>> listCategory(PageDTO dto) {
        PageVO<TagVO> list = tagService.getTagList(dto);
        return Result.success(list);
    }

    @Operation(summary = "更新标签")
    @PutMapping
    public Result<?> updateCategory(@Valid @RequestBody TagUpdateDTO dto) {
        boolean b = tagService.updateTag(dto);
        return b ? Result.success() : Result.error();
    }

    @Operation(summary = "删除标签")
    @DeleteMapping("/{id}")
    public Result<?> deleteCategory(@PathVariable Long id) {
        boolean b = tagService.deleteTag(id);
        return b ? Result.success() : Result.error();
    }
}
