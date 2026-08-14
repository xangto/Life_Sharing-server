package xangto.projects.life.api.moment.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MomentPublishDTO {
    @NotNull
    private Long id;
    @NotNull
    private Boolean isPublished;
}
