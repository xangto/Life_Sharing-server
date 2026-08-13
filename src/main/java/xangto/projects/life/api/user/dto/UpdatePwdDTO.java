package xangto.projects.life.api.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "更新密码参数")
@Data
public class UpdatePwdDTO {
    @NotBlank
    @Schema(description = "用户id")
    private Long userId;
    @NotBlank
    @Schema(description = "用户名")
    private String username;
    @NotBlank
    @Schema(description = "旧密码")
    private String oldPwd;
    @NotBlank
    @Schema(description = "新密码")
    private String newPwd;
}
