package xangto.projects.life.api.tag.service;

import com.baomidou.mybatisplus.extension.repository.IRepository;
import xangto.projects.life.api.tag.dto.TagCreateDTO;
import xangto.projects.life.api.tag.dto.TagUpdateDTO;
import xangto.projects.life.api.tag.entity.TagEntity;
import xangto.projects.life.api.tag.vo.TagVO;
import xangto.projects.life.common.PageDTO;
import xangto.projects.life.common.PageVO;

public interface TagService extends IRepository<TagEntity> {
    boolean createTag(TagCreateDTO dto);

    PageVO<TagVO> getTagList(PageDTO dto);

    boolean deleteTag(Long id);

    boolean updateTag(TagUpdateDTO dto);
}
