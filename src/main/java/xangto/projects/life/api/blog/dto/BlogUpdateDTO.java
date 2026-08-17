package xangto.projects.life.api.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogUpdateDTO {
    @Schema(type = "string")
    @NotNull
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String firstPicture;
    private String description;
    @NotBlank
    private String content;
    @NotNull
    private Boolean isPublished;
    @NotNull
    private Integer words;
    @NotNull
    private Integer readTime;
    @Schema(type = "string")
    @NotNull
    private Long categoryId;
    private String tags;
}
