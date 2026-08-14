package xangto.projects.life.api.moment.service;

import com.baomidou.mybatisplus.extension.repository.IRepository;
import xangto.projects.life.api.moment.dto.MomentCreateDTO;
import xangto.projects.life.api.moment.dto.MomentUpdateDTO;
import xangto.projects.life.api.moment.entity.MomentEntity;
import xangto.projects.life.api.moment.vo.MomentVO;
import xangto.projects.life.common.PageDTO;
import xangto.projects.life.common.PageVO;

public interface MomentService extends IRepository<MomentEntity> {
    boolean createMoment(MomentCreateDTO dto);

    PageVO<MomentVO> getMomentList(PageDTO dto);

    boolean deleteMoment(Long id);

    boolean updateMoment(MomentUpdateDTO dto);
}
