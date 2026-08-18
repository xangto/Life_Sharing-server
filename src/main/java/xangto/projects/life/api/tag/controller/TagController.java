package xangto.projects.life.api.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xangto.projects.life.api.tag.entity.TagEntity;
import xangto.projects.life.api.tag.service.TagService;
import xangto.projects.life.common.OptionVO;
import xangto.projects.life.common.Result;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "标签管理")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @Operation(summary = "标签列表")
    @GetMapping("/list")
    public Result<List<OptionVO>> listTag() {
        List<TagEntity> entityList = tagService.list();
        List<OptionVO> optionVOList = new ArrayList<>();
        entityList.forEach(e -> {
            OptionVO optionVO = new OptionVO(e.getName(), e.getId().toString());
            optionVOList.add(optionVO);
        });
        return Result.success(optionVOList);
    }
}
