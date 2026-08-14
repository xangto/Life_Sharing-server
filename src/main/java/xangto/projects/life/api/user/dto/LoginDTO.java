package xangto.projects.life.api.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "登录请求参数")
public class LoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
