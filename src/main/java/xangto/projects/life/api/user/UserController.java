package xangto.projects.life.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xangto.projects.life.common.Result;
import xangto.projects.life.utils.JWTUtils;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtils jwtUtils;

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
        boolean flag = userService.registerUser(user);
        if (flag) {
            return Result.success("注册成功");
        } else {
            return Result.error("注册失败");
        }
    }
}
