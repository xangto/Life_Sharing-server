package xangto.projects.life.api.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import org.springframework.stereotype.Service;
import xangto.projects.life.api.comment.converter.CommentConverter;
import xangto.projects.life.api.comment.dto.CommentCreateDTO;
import xangto.projects.life.api.comment.dto.CommentListQueryDTO;
import xangto.projects.life.api.comment.entity.CommentEntity;
import xangto.projects.life.api.comment.mapper.CommentMapper;
import xangto.projects.life.api.comment.service.CommentService;
import xangto.projects.life.api.comment.vo.CommentVO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.utils.TreeUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl extends CrudRepository<CommentMapper, CommentEntity> implements CommentService {
    @Override
    public PageVO<CommentVO> getList(CommentListQueryDTO dto) {
        LambdaQueryWrapper<CommentEntity> wrapper = new LambdaQueryWrapper<CommentEntity>()
                .eq(CommentEntity::getType, 0) // 普通文章的评论
                .eq(CommentEntity::getParentId, 0L) // 只查找根评论
                .eq(dto.getBlogId() != null, CommentEntity::getBlogId, dto.getBlogId())
                .orderByDesc(CommentEntity::getCreateTime);
        Page<CommentEntity> page = this.page(dto.toPage(), wrapper);

        List<Long> idList = page.getRecords().stream().map(CommentEntity::getId).toList();

        if (idList.isEmpty()) {
            return PageVO.from(page, new ArrayList<>());
        }

        List<CommentEntity> entityList = this.list(
                new LambdaQueryWrapper<CommentEntity>()
                        .in(CommentEntity::getId, idList)
                        .or()
                        .in(CommentEntity::getParentId, idList)
                        .orderByDesc(CommentEntity::getCreateTime)
        );
        List<CommentVO> voList = CommentConverter.INSTANCE.toVOList(entityList);
        // todo  转为树形,前端限制只能回复一层，所有后续回复都放在根评论下；
        List<CommentVO> tree = TreeUtils.buildTree(voList, CommentVO::getId, CommentVO::getParentId, CommentVO::setChildren);

        return PageVO.from(page, tree);
    }

    @Override
    public boolean deleteCommentById(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean createComment(CommentCreateDTO dto) {
        CommentEntity entity = CommentConverter.INSTANCE.toEntity(dto);
        return this.save(entity);
    }
}
