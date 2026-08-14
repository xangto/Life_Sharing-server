package xangto.projects.life.api.moment.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MomentUpdateDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String content;
    @NotNull
    private Boolean isPublished;
}
