package xangto.projects.life.api.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xangto.projects.life.common.PageDTO;

@EqualsAndHashCode(callSuper = false)
@Data
public class BlogListDTO extends PageDTO {
    @Schema(type = "string")
    private Long categoryId;
    private String title;
}
