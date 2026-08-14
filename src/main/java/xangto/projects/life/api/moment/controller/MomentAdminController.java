package xangto.projects.life.api.moment.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xangto.projects.life.api.moment.dto.MomentCreateDTO;
import xangto.projects.life.api.moment.dto.MomentPublishDTO;
import xangto.projects.life.api.moment.dto.MomentUpdateDTO;
import xangto.projects.life.api.moment.entity.MomentEntity;
import xangto.projects.life.api.moment.service.MomentService;
import xangto.projects.life.api.moment.vo.MomentVO;
import xangto.projects.life.common.PageDTO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.Result;

@Tag(name = "动态管理 admin")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/moment")
public class MomentAdminController {
    private final MomentService momentService;

    @Operation(summary = "新建动态")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Result<?> createMoment(@Valid @RequestBody MomentCreateDTO dto) {
        boolean flag = momentService.createMoment(dto);
        return flag ? Result.success() : Result.error();
    }

    @Operation(summary = "动态列表")
    @GetMapping("/list")
    public Result<PageVO<MomentVO>> listMoment(@Valid PageDTO dto) {
        PageVO<MomentVO> list = momentService.getMomentList(dto);
        return Result.success(list);
    }

    @Operation(summary = "更新动态")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public Result<?> updateMoment(@Valid @RequestBody MomentUpdateDTO dto) {
        boolean b = momentService.updateMoment(dto);
        return b ? Result.success() : Result.error();
    }

    @Operation(summary = "更改动态发布状态")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/publish")
    public Result<?> updatePublishStatus(@Valid @RequestBody MomentPublishDTO dto) {
        UpdateWrapper<MomentEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", dto.getId()).set("is_published", dto.getIsPublished());
        boolean b = momentService.update(wrapper);
        return b ? Result.success() : Result.error();
    }

    @Operation(summary = "删除动态")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Result<?> deleteMoment(@PathVariable @Min(1) Long id) {
        boolean b = momentService.deleteMoment(id);
        return b ? Result.success() : Result.error();
    }
}
