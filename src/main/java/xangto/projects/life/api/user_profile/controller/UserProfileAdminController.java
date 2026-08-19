package xangto.projects.life.api.user_profile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xangto.projects.life.api.user_profile.dto.UserProfileDTO;
import xangto.projects.life.api.user_profile.service.UserProfileService;
import xangto.projects.life.api.user_profile.vo.UserProfileVO;
import xangto.projects.life.common.Result;

@Tag(name = "个人资料 admin")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/user_profile")
public class UserProfileAdminController {
    private final UserProfileService userProfileService;

    @Operation(summary = "获取信息")
    @GetMapping
    public Result<UserProfileVO> getInfo() {
        UserProfileVO vo = userProfileService.getUserProfileInfo();
        return Result.success(vo);
    }

    @Operation(summary = "更新信息")
    @PutMapping
    public Result<?> updateInfo(@Valid @RequestBody UserProfileDTO dto) {
        boolean b = userProfileService.updateUserProfile(dto);
        return b ? Result.success() : Result.error();
    }

//    @Operation(summary = "新增信息")
//    @PostMapping
//    public Result<?> addInfo(@Valid @RequestBody UserProfileAddDTO dto) {
//        boolean b = userProfileService.addUserProfile(dto);
//        return b ? Result.success() : Result.error();
//    }
}
