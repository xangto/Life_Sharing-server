package xangto.projects.life.api.user_profile.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@TableName(value = "user_profile")
@Data
public class UserProfileEntity implements Serializable {
    @TableId
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