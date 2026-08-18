package xangto.projects.life.api.comment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "comment")
@Data
public class CommentEntity implements Serializable {
    @TableId
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private String ip;
    private Long blogId;
    private String website;
    private Long parentId;
    private Integer type;
    private Boolean isAdminComment;
    private Boolean isPublished;
    private LocalDateTime createTime;
}