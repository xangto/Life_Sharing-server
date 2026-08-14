package xangto.projects.life.api.blog.vo;

import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import xangto.projects.life.common.LongToStringSerializer;

import java.time.LocalDateTime;

@Data
public class BlogVO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;
    private String title;
    private String firstPicture;
    private String content;
    private Boolean isPublished;
    private Integer views;
    private Integer words;
    private Integer readTime;
    private Boolean isTop;
    private LocalDateTime createTime;
}
