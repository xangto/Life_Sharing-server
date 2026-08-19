package xangto.projects.life.api.friend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FriendCreateDTO {
    @NotBlank
    private String nickname;
    @NotBlank
    private String description;
    @NotBlank
    private String website;
    @NotBlank
    private String avatar;
}
