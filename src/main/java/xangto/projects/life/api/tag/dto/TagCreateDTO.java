package xangto.projects.life.api.tag.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagCreateDTO {
    @NotBlank
    private String name;
    private String color;
}
