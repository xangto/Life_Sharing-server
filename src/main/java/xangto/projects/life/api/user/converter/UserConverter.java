package xangto.projects.life.api.user.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import xangto.projects.life.api.user.dto.RegisterDTO;
import xangto.projects.life.api.user.entity.UserEntity;
import xangto.projects.life.api.user.vo.UserVO;

@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    UserEntity registerUser(RegisterDTO user);

    // Entity -> VO：username/nickname/avatar/email 按同名属性自动映射，
    // Entity 中其余字段在 VO 中不存在，MapStruct 自动忽略（这些 @Mapping target 不存在于 VO，会使编译报错，已移除）
    UserVO toVO(UserEntity user);
}
