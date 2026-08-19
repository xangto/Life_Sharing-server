package xangto.projects.life.api.friend.vo;

import lombok.Data;

@Data
public class FriendVO {
    private String id;
    private String nickname;
    private String description;
    private String website;
    private String avatar;
    private Boolean isPublished;
    private Integer views;
    private String createTime;
}
