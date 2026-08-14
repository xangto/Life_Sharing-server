package xangto.projects.life.api.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xangto.projects.life.api.blog.dto.BlogCreateDTO;
import xangto.projects.life.api.blog.dto.BlogListDTO;
import xangto.projects.life.api.blog.service.impl.BlogService;
import xangto.projects.life.api.blog.vo.BlogVO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.Result;
import xangto.projects.life.utils.JWTUtils;

@Tag(name = "文章管理 admin")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/blog")
public class BlogAdminController {
    private final BlogService blogService;
    private final JWTUtils jwtUtils;

    @Operation(summary = "文章列表")
    @GetMapping("/list")
    public Result<PageVO<BlogVO>> getBlogList(BlogListDTO dto) {
        PageVO<BlogVO> vo = blogService.getBlogList(dto);
        return Result.success(vo);
    }

    @Operation(summary = "新增文章")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Result<?> createBlog(HttpServletRequest req, @Valid @RequestBody BlogCreateDTO dto) {
        String authHeader = req.getHeader("Authorization");
        String token = authHeader.substring(7);
        Long userId = jwtUtils.getUserId(token);
        boolean b = blogService.createBlog(dto, userId);
        return b ? Result.success() : Result.error();
    }
}
