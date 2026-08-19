package xangto.projects.life.api.friend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import org.springframework.stereotype.Service;
import xangto.projects.life.api.friend.converter.FriendConverter;
import xangto.projects.life.api.friend.dto.FriendCreateDTO;
import xangto.projects.life.api.friend.dto.FriendPageDTO;
import xangto.projects.life.api.friend.dto.FriendPublishDTO;
import xangto.projects.life.api.friend.entity.FriendEntity;
import xangto.projects.life.api.friend.mapper.FriendMapper;
import xangto.projects.life.api.friend.service.FriendService;
import xangto.projects.life.api.friend.vo.FriendVO;
import xangto.projects.life.common.PageVO;

import java.util.List;

@Service
public class FriendServiceImpl extends CrudRepository<FriendMapper, FriendEntity> implements FriendService {
    @Override
    public PageVO<FriendVO> getFriendPageList(FriendPageDTO dto) {
        LambdaQueryWrapper<FriendEntity> wrapper = new LambdaQueryWrapper<FriendEntity>()
                .like(dto.getNickname() != null, FriendEntity::getAvatar, dto.getNickname())
                .like(dto.getWebsite() != null, FriendEntity::getWebsite, dto.getWebsite())
                .orderByDesc(FriendEntity::getCreateTime);
        Page<FriendEntity> page = this.page(dto.toPage(), wrapper);
        List<FriendVO> voList = FriendConverter.INSTANCE.toVOList(page.getRecords());
        return PageVO.from(page, voList);
    }

    @Override
    public boolean addFriend(FriendCreateDTO friend) {
        FriendEntity entity = new FriendEntity();
        entity.setNickname(friend.getNickname());
        entity.setWebsite(friend.getWebsite());
        entity.setDescription(friend.getDescription());
        entity.setAvatar(friend.getAvatar());
        return this.save(entity);
    }

    @Override
    public boolean deleteFriend(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean updateFriendPublish(FriendPublishDTO dto) {
        return this.update(
                new LambdaUpdateWrapper<FriendEntity>()
                        .set(FriendEntity::getIsPublished, dto.getIsPublished())
                        .eq(FriendEntity::getId, dto.getId())
        );
    }
}
