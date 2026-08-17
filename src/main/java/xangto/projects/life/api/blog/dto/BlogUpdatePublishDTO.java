package xangto.projects.life.api.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogUpdatePublishDTO {
    @Schema(type = "string")
    @NotNull
    private Long id;
    @NotNull
    private Boolean isPublished;
}
