package xangto.projects.life.api.moment.vo;

import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import xangto.projects.life.common.LongToStringSerializer;

import java.time.LocalDateTime;

@Data
public class MomentVO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;
    private String content;
    private Integer likes;
    private Boolean isPublished;
    private String createTime;
}
