package xangto.projects.life.api.moment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xangto.projects.life.api.moment.service.MomentService;
import xangto.projects.life.api.moment.vo.MomentVO;
import xangto.projects.life.common.PageDTO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.Result;

@Tag(name = "动态管理")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/moment")
public class MomentController {
    private final MomentService momentService;

    @Operation(summary = "动态列表")
    @GetMapping("/list")
    public Result<PageVO<MomentVO>> listMoment(@Valid PageDTO dto) {
        PageVO<MomentVO> list = momentService.getMomentList(dto);
        // todo 只查找公布的
        return Result.success(list);
    }
}
