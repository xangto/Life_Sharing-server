package xangto.projects.life.api.user_profile.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.Jackson3TypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 注意：social_links / custom 是 MySQL json 列，JDBC 取出为字符串，
 * 必须通过 @TableField(typeHandler = JacksonTypeHandler.class) 转成 List 对象，
 * 且 @TableName 必须开启 autoResultMap = true，否则查询时 typeHandler 不生效（字段为 null）。
 */
@TableName(value = "user_profile", autoResultMap = true)
@Data
public class UserProfileEntity implements Serializable {
    @TableId
    private Long id;
    private String nickName;
    private String avatar;
    private String bio;
    @TableField(typeHandler = Jackson3TypeHandler.class)
    private List<SocialLink> socialLinks;
    @TableField(typeHandler = Jackson3TypeHandler.class)
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