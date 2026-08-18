package xangto.projects.life.api.comment.service;

import com.baomidou.mybatisplus.extension.repository.IRepository;
import xangto.projects.life.api.comment.dto.CommentCreateDTO;
import xangto.projects.life.api.comment.dto.CommentListQueryDTO;
import xangto.projects.life.api.comment.entity.CommentEntity;
import xangto.projects.life.api.comment.vo.CommentVO;
import xangto.projects.life.common.PageVO;

public interface CommentService extends IRepository<CommentEntity> {
    PageVO<CommentVO> getList(CommentListQueryDTO dto);

    boolean deleteCommentById(Long id);

    boolean createComment(CommentCreateDTO dto);
}
