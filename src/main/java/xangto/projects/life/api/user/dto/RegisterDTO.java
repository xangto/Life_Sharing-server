package xangto.projects.life.api.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "用户注册")
public class RegisterDTO {
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Schema(description = "用户密码")
    private String password;
    @NotBlank(message = "用户昵称不能为空")
    @Schema(description = "用户昵称")
    private String nickname;
    @Schema(description = "用户头像url")
    private String avatar;
    @Schema(description = "用户邮箱")
    private String email;
}
