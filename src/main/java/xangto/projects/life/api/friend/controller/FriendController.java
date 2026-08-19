package xangto.projects.life.api.friend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xangto.projects.life.api.friend.dto.FriendCreateDTO;
import xangto.projects.life.api.friend.dto.FriendPageDTO;
import xangto.projects.life.api.friend.service.FriendService;
import xangto.projects.life.api.friend.vo.FriendVO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.Result;

@Tag(name = "友链管理")
@RequiredArgsConstructor
@RequestMapping("/friend")
@RestController
public class FriendController {
    public final FriendService friendService;

    @Operation(summary = "友链列表")
    @GetMapping("/list")
    public Result<PageVO<FriendVO>> getFriendPageList(@Valid FriendPageDTO dto) {
        PageVO<FriendVO> vo = friendService.getFriendPageList(dto);
        return Result.success(vo);
    }

    @Operation(summary = "新增友链")
    @PostMapping
    public Result<?> addFriend(@Valid @RequestBody FriendCreateDTO dto) {
        boolean b = friendService.addFriend(dto);
        return b ? Result.success() : Result.error();
    }
}
