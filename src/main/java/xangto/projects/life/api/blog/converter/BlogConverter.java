package xangto.projects.life.api.blog.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import xangto.projects.life.api.blog.dto.BlogCreateDTO;
import xangto.projects.life.api.blog.dto.BlogUpdateDTO;
import xangto.projects.life.api.blog.entity.BlogEntity;
import xangto.projects.life.api.blog.vo.BlogVO;

import java.util.List;

@Mapper
public interface BlogConverter {
    BlogConverter INSTANCE = Mappers.getMapper(BlogConverter.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "isTop", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    BlogEntity toEntity(BlogCreateDTO dto);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    BlogEntity toEntity(BlogUpdateDTO dto);

    @Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "updateTime", target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "content", ignore = true)
    BlogVO toVO(BlogEntity entity);

    List<BlogVO> toVOList(List<BlogEntity> entities);
}
