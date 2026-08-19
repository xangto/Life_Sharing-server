package xangto.projects.life.api.user_profile.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileAddDTO {
    @NotBlank
    private String nickName;
    @NotBlank
    private String avatar;
    @NotBlank
    private String bio;
    @Valid
    @NotNull
    private List<SocialLink> socialLinks;
    @Valid
    private List<CustomItem> custom;

    @Data
    public static class SocialLink {
        @NotBlank
        private String iconName;
        private String color;
        @NotBlank
        private String url;
    }

    @Data
    public static class CustomItem {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
    }
}
