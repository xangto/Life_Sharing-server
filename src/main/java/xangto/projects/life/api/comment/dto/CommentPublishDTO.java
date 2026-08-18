package xangto.projects.life.api.comment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentPublishDTO {
    @NotNull
    @Min(1)
    private Long id;
    @NotNull
    private Boolean isPublished;
}
