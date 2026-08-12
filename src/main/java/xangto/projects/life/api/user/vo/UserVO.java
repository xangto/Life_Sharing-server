package xangto.projects.life.api.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserVO {
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "用户昵称")
    private String nickname;
    @Schema(description = "用户头像url")
    private String avatar;
    @Schema(description = "用户邮箱")
    private String email;
}
