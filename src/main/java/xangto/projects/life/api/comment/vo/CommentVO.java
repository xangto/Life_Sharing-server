package xangto.projects.life.api.comment.vo;

import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import xangto.projects.life.common.LongToStringSerializer;

import java.util.List;

@Data
public class CommentVO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private String ip;
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long blogId;
    private String website;
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long parentId;
    private Integer type;
    private Boolean isAdminComment;
    private Boolean isPublished;
    private String createTime;

    private List<CommentVO> children;
}
