package xangto.projects.life.api.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryUpdateDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
}
