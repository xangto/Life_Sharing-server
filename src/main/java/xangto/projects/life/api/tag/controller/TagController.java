package xangto.projects.life.api.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xangto.projects.life.api.tag.service.TagService;
import xangto.projects.life.api.tag.vo.TagVO;
import xangto.projects.life.common.PageDTO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.Result;

@Tag(name = "标签管理")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @Operation(summary = "标签列表")
    @GetMapping("/list")
    public Result<PageVO<TagVO>> listTag(@Valid PageDTO dto) {
        PageVO<TagVO> list = tagService.getTagList(dto);
        return Result.success(list);
    }
}
