package xangto.projects.life.api.category.vo;

import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import xangto.projects.life.common.LongToStringSerializer;

@Data
public class CategoryVO {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;
    private String name;
}
