package xangto.projects.life.api.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xangto.projects.life.api.comment.dto.CommentCreateDTO;
import xangto.projects.life.api.comment.dto.CommentListQueryDTO;
import xangto.projects.life.api.comment.service.CommentService;
import xangto.projects.life.api.comment.vo.CommentVO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.Result;

@Slf4j
@Tag(name = "评论管理")
@Validated
@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "评论列表")
    @GetMapping
    public Result<PageVO<CommentVO>> getCommentList(@Valid CommentListQueryDTO dto) {
        PageVO<CommentVO> list = commentService.getList(dto);

        // todo 只查找公布的评论
        return Result.success(list);
    }

    @Operation(summary = "新增评论")
    @PostMapping
    public Result<?> createComment(@Valid @RequestBody CommentCreateDTO dto) {
        boolean b = commentService.createComment(dto);
        return b ? Result.success() : Result.error();
    }
}
