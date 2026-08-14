package xangto.projects.life.api.moment.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import org.springframework.stereotype.Service;
import xangto.projects.life.api.moment.dto.MomentCreateDTO;
import xangto.projects.life.api.moment.dto.MomentUpdateDTO;
import xangto.projects.life.api.moment.entity.MomentEntity;
import xangto.projects.life.api.moment.mapper.MomentMapper;
import xangto.projects.life.api.moment.service.MomentService;
import xangto.projects.life.api.moment.vo.MomentVO;
import xangto.projects.life.common.PageDTO;
import xangto.projects.life.common.PageVO;

import java.util.List;

@Service
public class MomentServiceImpl extends CrudRepository<MomentMapper, MomentEntity> implements MomentService {


    @Override
    public boolean createMoment(MomentCreateDTO dto) {
        MomentEntity entity = new MomentEntity();
        entity.setContent(dto.getContent());
        entity.setIsPublished(dto.getIsPublished());
        // 新建动态点赞数从 0 开始
        entity.setLikes(0);
        return this.save(entity);
    }

    @Override
    public PageVO<MomentVO> getMomentList(PageDTO dto) {
        Page<MomentEntity> page = this.page(dto.toPage());
        List<MomentEntity> list = page.getRecords();
        List<MomentVO> voList = list.stream().map(e -> {
            MomentVO vo = new MomentVO();
            vo.setId(e.getId());
            vo.setContent(e.getContent());
            vo.setLikes(e.getLikes());
            vo.setIsPublished(e.getIsPublished());
            vo.setCreateTime(e.getCreateTime());
            return vo;
        }).toList();
        return PageVO.from(page, voList);
    }

    @Override
    public boolean updateMoment(MomentUpdateDTO dto) {
        MomentEntity entity = new MomentEntity();
        entity.setId(dto.getId());
        entity.setContent(dto.getContent());
        entity.setIsPublished(dto.getIsPublished());
        return this.updateById(entity);
    }

    @Override
    public boolean deleteMoment(Long id) {
        return this.removeById(id);
    }
}
