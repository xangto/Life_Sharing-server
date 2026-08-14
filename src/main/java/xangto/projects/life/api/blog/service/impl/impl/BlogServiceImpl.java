package xangto.projects.life.api.blog.service.impl.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xangto.projects.life.api.blog.converter.BlogConverter;
import xangto.projects.life.api.blog.dto.BlogCreateDTO;
import xangto.projects.life.api.blog.dto.BlogListDTO;
import xangto.projects.life.api.blog.entity.BlogEntity;
import xangto.projects.life.api.blog.mapper.BlogMapper;
import xangto.projects.life.api.blog.service.impl.BlogService;
import xangto.projects.life.api.blog.vo.BlogVO;
import xangto.projects.life.api.category.entity.CategoryEntity;
import xangto.projects.life.api.category.mapper.CategoryMapper;
import xangto.projects.life.common.BusinessException;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.ResultCode;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl extends CrudRepository<BlogMapper, BlogEntity> implements BlogService {
    private final CategoryMapper categoryMapper;

    @Override
    public PageVO<BlogVO> getBlogList(BlogListDTO dto) {

        Page<BlogEntity> page = this.page(dto.toPage());
        LambdaQueryWrapper<BlogEntity> wrapper = new LambdaQueryWrapper<BlogEntity>()
                .eq(dto.getCategoryId() != null, BlogEntity::getCategoryId, dto.getCategoryId())
                .eq(dto.getTitle() != null, BlogEntity::getTitle, dto.getTitle());
        List<BlogEntity> list = this.list(page, wrapper);
        List<BlogVO> voList = BlogConverter.INSTANCE.toVOList(list);
        return PageVO.from(page, voList);
    }

    @Override
    public boolean createBlog(BlogCreateDTO dto, Long userId) {
        // todo 校验 categoryId 是否存在
        CategoryEntity categoryEntity = categoryMapper.selectById(dto.getCategoryId());
        if (categoryEntity == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "分类id不存在");
        }
        if (StringUtils.isNotBlank(dto.getTags())) {
            // 将标签保存到中间表
            String[] tagIdList = dto.getTags().split(",");

        }
        BlogEntity entity = BlogConverter.INSTANCE.toEntity(dto);
        entity.setUserId(userId);
        return this.save(entity);
    }
}
