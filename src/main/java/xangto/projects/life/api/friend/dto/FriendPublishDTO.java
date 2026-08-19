package xangto.projects.life.api.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FriendPublishDTO {
    @NotNull
    private Long id;
    @NotNull
    private Boolean isPublished;
}
