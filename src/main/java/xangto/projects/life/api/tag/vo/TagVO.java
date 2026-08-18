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

    public TagVO() {
    }

    public TagVO(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}
