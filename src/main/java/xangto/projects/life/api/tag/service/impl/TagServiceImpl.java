package xangto.projects.life.api.tag.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import org.springframework.stereotype.Service;
import xangto.projects.life.api.tag.dto.TagCreateDTO;
import xangto.projects.life.api.tag.dto.TagUpdateDTO;
import xangto.projects.life.api.tag.entity.TagEntity;
import xangto.projects.life.api.tag.mapper.TagMapper;
import xangto.projects.life.api.tag.service.TagService;
import xangto.projects.life.api.tag.vo.TagVO;
import xangto.projects.life.common.PageDTO;
import xangto.projects.life.common.PageVO;

import java.util.List;

@Service
public class TagServiceImpl extends CrudRepository<TagMapper, TagEntity> implements TagService {

    @Override
    public boolean createTag(TagCreateDTO dto) {
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        entity.setColor(dto.getColor());
        return this.save(entity);
    }

    @Override
    public PageVO<TagVO> getTagList(PageDTO dto) {
        Page<TagEntity> page = this.page(dto.toPage());
        List<TagEntity> list = page.getRecords();
        List<TagVO> voList = list.stream().map(e -> {
            TagVO vo = new TagVO();
            vo.setId(e.getId());
            vo.setName(e.getName());
            vo.setColor(e.getColor());
            return vo;
        }).toList();
        return PageVO.from(page, voList);
    }

    @Override
    public boolean updateTag(TagUpdateDTO dto) {
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        entity.setColor(dto.getColor());
        entity.setId(dto.getId());
        return this.updateById(entity);
    }

    @Override
    public boolean deleteTag(Long id) {
        if (id <= 0) return false;
        return this.removeById(id);
    }
}
