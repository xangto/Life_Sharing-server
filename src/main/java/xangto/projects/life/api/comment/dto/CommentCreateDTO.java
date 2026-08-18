package xangto.projects.life.api.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentCreateDTO {
    @NotBlank
    private String nickname;
    private String email;
    @NotBlank
    private String content;
    private String avatar;
    @Schema(hidden = true)
    private String ip;
    @NotNull
    @Schema(type = "string")
    private Long blogId;
    private String website;
    @Schema(type = "string")
    private Long parentId;
    @NotNull
    private Integer type;
    private Boolean isAdminComment;
    private Boolean isPublished;
}
