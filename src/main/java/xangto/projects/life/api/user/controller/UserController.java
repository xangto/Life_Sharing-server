package xangto.projects.life.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import xangto.projects.life.api.user.dto.LoginDTO;
import xangto.projects.life.api.user.dto.RegisterDTO;
import xangto.projects.life.api.user.dto.UpdatePwdDTO;
import xangto.projects.life.api.user.service.UserService;
import xangto.projects.life.api.user.vo.LoginVO;
import xangto.projects.life.api.user.vo.UserVO;
import xangto.projects.life.common.Result;
import xangto.projects.life.utils.JWTUtils;

@Tag(name = "用户管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/user")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTUtils jwtUtils;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<?> login(@Valid @RequestBody LoginDTO user) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authToken);
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        if (userDetails != null) {
            UserVO userVO = userService.findByUsername(userDetails.getUsername());
            String token = jwtUtils.genJWT(userVO.getUsername());
            LoginVO vo = new LoginVO();
            vo.setToken(token);
            vo.setUser(userVO);
            return Result.success(vo);
        } else {
            return Result.error();
        }

    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result<?> register(@Valid @RequestBody RegisterDTO user) {
        // 业务失败（如用户名已存在）由 GlobalExceptionHandler 统一返回 409，走到这里即注册成功
        userService.registerUser(user);
        return Result.success("注册成功");
    }

    @Operation(summary = "更新密码")
    @PreAuthorize("hasRole('ADMIN') and authentication.name == #dto.username") // 只有admin才能改自己的密码,其他用户的密码都不让改
    @PostMapping("/update/password")
    public Result<?> updatePassword(@RequestBody UpdatePwdDTO dto) {
        userService.updateUser(dto);
        return Result.success();
    }

    @Operation(summary = "删除用户")
//    @PreAuthorize("hasAuthority('user:delete')")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        boolean b = userService.removeById(id);
        if (b) {
            return Result.success();
        } else {
            return Result.error();
        }
    }
}
