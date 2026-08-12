package xangto.projects.life.api.user.vo;

import lombok.Data;

@Data
public class LoginVO {
    private String token;
    private UserVO user;
}
