package xangto.projects.life.api.tag.vo;

import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import xangto.projects.life.common.LongToStringSerializer;

@Data
public class TagVO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;
    private String name;
    private String color;
}
