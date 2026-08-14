package xangto.projects.life.api.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogCreateDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String firstPicture;
    @NotBlank
    private String content;
    @NotNull
    private Boolean isPublished;
    @NotNull
    private Integer words;
    @NotNull
    private Integer readTime;
    @NotNull
    private Long categoryId;
    private String tags;
}
