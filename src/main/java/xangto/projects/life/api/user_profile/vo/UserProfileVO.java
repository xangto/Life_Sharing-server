package xangto.projects.life.api.user_profile.vo;

import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import xangto.projects.life.common.LongToStringSerializer;

import java.util.List;

@Data
public class UserProfileVO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;
    private String nickName;
    private String avatar;
    private String bio;
    private List<SocialLink> socialLinks;
    private List<CustomItem> custom;

    @Data
    public static class SocialLink {
        private String iconName;
        private String color;
        private String url;
    }

    @Data
    public static class CustomItem {
        private String title;
        private String content;
    }
}
