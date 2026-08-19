package xangto.projects.life.api.friend.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import xangto.projects.life.api.friend.entity.FriendEntity;
import xangto.projects.life.api.friend.vo.FriendVO;

import java.util.List;

@Mapper
public interface FriendConverter {
    FriendConverter INSTANCE = Mappers.getMapper(FriendConverter.class);

    @Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    FriendVO toVO(FriendEntity entity);

    List<FriendVO> toVOList(List<FriendEntity> entity);
}
