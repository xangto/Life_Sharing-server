package xangto.projects.life.api.comment.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import xangto.projects.life.api.comment.dto.CommentCreateDTO;
import xangto.projects.life.api.comment.entity.CommentEntity;
import xangto.projects.life.api.comment.vo.CommentVO;

import java.util.List;

@Mapper
public interface CommentConverter {
    CommentConverter INSTANCE = Mappers.getMapper(CommentConverter.class);

    @Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    CommentVO toVO(CommentEntity entity);

    List<CommentVO> toVOList(List<CommentEntity> entityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    CommentEntity toEntity(CommentCreateDTO dto);

}
