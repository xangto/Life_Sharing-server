package xangto.projects.life.api.friend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "friend")
@Data
public class FriendEntity implements Serializable {
    @TableId
    private Long id;
    private String nickname;
    private String description;
    private String website;
    private String avatar;
    private Boolean isPublished;
    private Integer views;
    private LocalDateTime createTime;
}