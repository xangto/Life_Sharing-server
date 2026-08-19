package xangto.projects.life.api.user_profile.service.impl;

import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import org.springframework.stereotype.Service;
import xangto.projects.life.api.user_profile.converter.UserProfileConverter;
import xangto.projects.life.api.user_profile.dto.UserProfileAddDTO;
import xangto.projects.life.api.user_profile.dto.UserProfileDTO;
import xangto.projects.life.api.user_profile.entity.UserProfileEntity;
import xangto.projects.life.api.user_profile.mapper.UserProfileMapper;
import xangto.projects.life.api.user_profile.service.UserProfileService;
import xangto.projects.life.api.user_profile.vo.UserProfileVO;

@Service
public class UserProfileServiceImpl extends CrudRepository<UserProfileMapper, UserProfileEntity> implements UserProfileService {
    @Override
    public UserProfileVO getUserProfileInfo() {
        UserProfileEntity entity = this.list().get(0);
        return UserProfileConverter.INSTANCE.toVO(entity);
    }

    @Override
    public boolean updateUserProfile(UserProfileDTO dto) {
        UserProfileEntity entity = UserProfileConverter.INSTANCE.toEntity(dto);
        return this.updateById(entity);
    }

    @Override
    public boolean addUserProfile(UserProfileAddDTO dto) {
        UserProfileEntity entity = UserProfileConverter.INSTANCE.toEntity(dto);
        return this.save(entity);
    }
}
