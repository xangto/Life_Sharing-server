package xangto.projects.life.api.friend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xangto.projects.life.common.PageDTO;

@EqualsAndHashCode(callSuper = false)
@Data
public class FriendPageDTO extends PageDTO {
    private String nickname;
    private String website;
}
