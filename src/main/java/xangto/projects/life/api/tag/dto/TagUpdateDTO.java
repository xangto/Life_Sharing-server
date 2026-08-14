package xangto.projects.life.api.tag.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagUpdateDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String color;
}
