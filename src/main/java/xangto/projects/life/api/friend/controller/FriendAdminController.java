package xangto.projects.life.api.friend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xangto.projects.life.api.friend.dto.FriendCreateDTO;
import xangto.projects.life.api.friend.dto.FriendPageDTO;
import xangto.projects.life.api.friend.dto.FriendPublishDTO;
import xangto.projects.life.api.friend.service.FriendService;
import xangto.projects.life.api.friend.vo.FriendVO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.Result;

@Tag(name = "友链管理 admin")
@RequiredArgsConstructor
@RequestMapping("/admin/friend")
@RestController
public class FriendAdminController {
    public final FriendService friendService;

    @Operation(summary = "友链列表")
    @GetMapping("/list")
    public Result<PageVO<FriendVO>> getFriendPageList(@Valid FriendPageDTO dto) {
        PageVO<FriendVO> vo = friendService.getFriendPageList(dto);
        return Result.success(vo);
    }

//    @Operation(summary = "新增友链")
//    @PostMapping
//    public Result<?> addFriend(@Valid @RequestBody FriendCreateDTO dto) {
//        boolean b = friendService.addFriend(dto);
//        return b ? Result.success() : Result.error();
//    }

    @Operation(summary = "删除友链")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Result<?> delFriend(@PathVariable Long id) {
        boolean b = friendService.deleteFriend(id);
        return b ? Result.success() : Result.error();
    }

    @Operation(summary = "友链公布状态")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/publish")
    public Result<?> updateFriendPublish(@Valid @RequestBody FriendPublishDTO dto) {
        boolean b = friendService.updateFriendPublish(dto);
        return b ? Result.success() : Result.error();
    }
}
