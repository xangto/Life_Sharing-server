package xangto.projects.life.api.comment.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xangto.projects.life.api.comment.dto.CommentListQueryDTO;
import xangto.projects.life.api.comment.dto.CommentPublishDTO;
import xangto.projects.life.api.comment.entity.CommentEntity;
import xangto.projects.life.api.comment.service.CommentService;
import xangto.projects.life.api.comment.vo.CommentVO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.Result;

@Slf4j
@Tag(name = "评论管理 admin")
@Validated
@RequiredArgsConstructor
@RequestMapping("/admin/comment")
@RestController
public class CommentAdminController {
    private final CommentService commentService;

    @Operation(summary = "评论列表")
    @GetMapping
    public Result<PageVO<CommentVO>> getCommentList(@Valid CommentListQueryDTO dto) {
        PageVO<CommentVO> list = commentService.getList(dto);

        return Result.success(list);
    }

    @Operation(summary = "删除评论")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Result<?> deleteCommentById(@PathVariable @Min(1) Long id) {
        boolean b = commentService.deleteCommentById(id);
        return b ? Result.success() : Result.error();
    }

    @Operation(summary = "修改评论公布状态")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/publish")
    public Result<?> updateCommentPublish(@Valid @RequestBody CommentPublishDTO dto) {
        boolean b = commentService.update(
                new LambdaUpdateWrapper<CommentEntity>()
                        .set(CommentEntity::getIsPublished, dto.getIsPublished())
                        .eq(CommentEntity::getId, dto.getId())
        );
        return b ? Result.success() : Result.error();
    }
}
