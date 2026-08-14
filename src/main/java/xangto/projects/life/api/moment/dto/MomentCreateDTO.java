package xangto.projects.life.api.moment.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MomentCreateDTO {
    @NotBlank
    private String content;
    @NotNull
    @Min(0)
    @Max(1)
    private Short isPublished;
}
