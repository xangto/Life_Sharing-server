package xangto.projects.life.api.blog.vo;

import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import xangto.projects.life.common.LongToStringSerializer;

@Data
public class BlogVO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;
    private String title;
    private String firstPicture;
    private String description;
    private String content;
    private Boolean isPublished;
    private Integer views;
    private Integer words;
    private Integer readTime;
    private Boolean isTop;
    private String updateTime;
    private String createTime;
}
