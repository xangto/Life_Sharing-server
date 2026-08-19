package xangto.projects.life.api.user_profile.service;

import com.baomidou.mybatisplus.extension.repository.IRepository;
import xangto.projects.life.api.user_profile.dto.UserProfileAddDTO;
import xangto.projects.life.api.user_profile.dto.UserProfileDTO;
import xangto.projects.life.api.user_profile.entity.UserProfileEntity;
import xangto.projects.life.api.user_profile.vo.UserProfileVO;

public interface UserProfileService extends IRepository<UserProfileEntity> {
    UserProfileVO getUserProfileInfo();

    boolean updateUserProfile(UserProfileDTO dto);

    boolean addUserProfile(UserProfileAddDTO dto);
}
