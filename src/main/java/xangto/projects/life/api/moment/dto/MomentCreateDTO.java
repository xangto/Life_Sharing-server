package xangto.projects.life.api.moment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MomentCreateDTO {
    @NotBlank
    private String content;
    @NotNull
    private Boolean isPublished;
}
