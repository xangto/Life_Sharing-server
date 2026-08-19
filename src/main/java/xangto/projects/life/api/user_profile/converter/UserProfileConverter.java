package xangto.projects.life.api.user_profile.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xangto.projects.life.api.user_profile.dto.UserProfileAddDTO;
import xangto.projects.life.api.user_profile.dto.UserProfileDTO;
import xangto.projects.life.api.user_profile.entity.UserProfileEntity;
import xangto.projects.life.api.user_profile.vo.UserProfileVO;

@Mapper
public interface UserProfileConverter {
    UserProfileConverter INSTANCE = Mappers.getMapper(UserProfileConverter.class);

    UserProfileVO toVO(UserProfileEntity entity);

    UserProfileEntity toEntity(UserProfileDTO dto);

    UserProfileEntity toEntity(UserProfileAddDTO dto);
}
