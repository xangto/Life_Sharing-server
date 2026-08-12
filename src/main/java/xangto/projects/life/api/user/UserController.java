package xangto.projects.life.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xangto.projects.life.common.Result;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<?> login(@Valid @RequestBody LoginDTO user) {

    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result<?> register(@Valid @RequestBody RegisterDTO user) {
        boolean flag = userService.registerUser(user);
        if (flag) {
            return Result.success("注册成功");
        } else {
            return Result.error("注册失败");
        }
    }
}
