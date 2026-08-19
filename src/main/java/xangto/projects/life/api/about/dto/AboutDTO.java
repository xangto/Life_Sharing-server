package xangto.projects.life.api.about.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AboutDTO {
    @NotNull
    private Integer id;
    private String title;
    @NotBlank
    private String content;
}
