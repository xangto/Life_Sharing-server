package xangto.projects.life.api.blog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xangto.projects.life.common.PageDTO;

@EqualsAndHashCode(callSuper = false)
@Data
public class BlogListDTO extends PageDTO {
    private Long categoryId;
    private String title;
}
