package xangto.projects.life.api.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户注册")
public class RegisterDTO {
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "用户密码")
    private String password;
    @Schema(description = "用户昵称")
    private String nickname;
    @Schema(description = "用户头像url")
    private String avatar;
    @Schema(description = "用户邮箱")
    private String email;
}
