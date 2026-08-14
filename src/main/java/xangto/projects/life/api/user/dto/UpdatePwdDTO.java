package xangto.projects.life.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePwdDTO {
    @NotNull
    private Long userId;
    @NotBlank
    private String username;
    @NotBlank
    private String oldPwd;
    @NotBlank
    private String newPwd;
}
