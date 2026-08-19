package xangto.projects.life.api.friend.service;

import com.baomidou.mybatisplus.extension.repository.IRepository;
import xangto.projects.life.api.friend.dto.FriendCreateDTO;
import xangto.projects.life.api.friend.dto.FriendPageDTO;
import xangto.projects.life.api.friend.dto.FriendPublishDTO;
import xangto.projects.life.api.friend.entity.FriendEntity;
import xangto.projects.life.api.friend.vo.FriendVO;
import xangto.projects.life.common.PageVO;

public interface FriendService extends IRepository<FriendEntity> {
    PageVO<FriendVO> getFriendPageList(FriendPageDTO dto);

    boolean addFriend(FriendCreateDTO dto);

    boolean deleteFriend(Long id);

    boolean updateFriendPublish(FriendPublishDTO dto);
}
