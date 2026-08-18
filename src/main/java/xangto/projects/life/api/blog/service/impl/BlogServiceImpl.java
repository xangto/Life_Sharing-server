package xangto.projects.life.api.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xangto.projects.life.api.blog.converter.BlogConverter;
import xangto.projects.life.api.blog.dto.BlogCreateDTO;
import xangto.projects.life.api.blog.dto.BlogListDTO;
import xangto.projects.life.api.blog.dto.BlogUpdateDTO;
import xangto.projects.life.api.blog.entity.BlogEntity;
import xangto.projects.life.api.blog.entity.BlogTagEntity;
import xangto.projects.life.api.blog.mapper.BlogMapper;
import xangto.projects.life.api.blog.service.BlogService;
import xangto.projects.life.api.blog.vo.BlogInfoVO;
import xangto.projects.life.api.blog.vo.BlogVO;
import xangto.projects.life.api.category.entity.CategoryEntity;
import xangto.projects.life.api.category.mapper.CategoryMapper;
import xangto.projects.life.api.tag.mapper.TagMapper;
import xangto.projects.life.common.BusinessException;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.Result;
import xangto.projects.life.common.ResultCode;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl extends CrudRepository<BlogMapper, BlogEntity> implements BlogService {
    private final CategoryMapper categoryMapper;
    /**
     * 标签 Mapper：批量校验标签 id 是否存在
     */
    private final TagMapper tagMapper;
    /**
     * 博客 Mapper：用于中间表 blog_tag 的自定义 SQL
     */
    private final BlogMapper blogMapper;

    @Override
    public PageVO<BlogVO> getBlogList(BlogListDTO dto) {
        LambdaQueryWrapper<BlogEntity> wrapper = new LambdaQueryWrapper<BlogEntity>()
                .eq(dto.getCategoryId() != null, BlogEntity::getCategoryId, dto.getCategoryId())
                .like(dto.getTitle() != null, BlogEntity::getTitle, dto.getTitle())
                .orderByDesc(BlogEntity::getIsPublished)
                .orderByDesc(BlogEntity::getIsTop)
                .orderByDesc(BlogEntity::getCreateTime);
        Page<BlogEntity> entityPage = this.page(dto.toPage(), wrapper);
        List<BlogVO> voList = BlogConverter.INSTANCE.toVOList(entityPage.getRecords());
        return PageVO.from(entityPage, voList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createBlog(BlogCreateDTO dto, Long userId) {
        // 校验 categoryId 是否存在
        CategoryEntity categoryEntity = categoryMapper.selectById(dto.getCategoryId());
        if (categoryEntity == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "分类id不存在");
        }
        BlogEntity entity = BlogConverter.INSTANCE.toEntity(dto);
        entity.setUserId(userId);
        // 初始化统计与置顶字段，避免落库为 NULL
        entity.setViews(0);
        entity.setIsTop(false);
        // 先保存博客拿到主键 id，中间表关联需要 blogId
        if (!this.save(entity)) {
            return false;
        }
        // 将标签保存到中间表 blog_tag
        if (StringUtils.isNotBlank(dto.getTags())) {
            // 解析并去重标签 id：重复 id 只存一条，避免唯一键冲突或重复脏数据
            Set<Long> tagIds = Arrays.stream(dto.getTags().split(","))
                    .map(String::trim)
                    .filter(StringUtils::isNotBlank)
                    .map(tagIdStr -> {
                        try {
                            return Long.valueOf(tagIdStr);
                        } catch (NumberFormatException e) {
                            throw new BusinessException(ResultCode.BAD_REQUEST, "标签id格式错误: " + tagIdStr);
                        }
                    })
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            // 批量校验标签是否存在，避免中间表写入不存在的 tag_id 脏数据
            if (!tagIds.isEmpty() && tagMapper.selectByIds(tagIds).size() != tagIds.size()) {
                throw new BusinessException(ResultCode.NOT_FOUND, "标签id不存在");
            }
            for (Long tagId : tagIds) {
                int i = blogMapper.saveBlogTag(entity.getId(), tagId);
                if (i != 1) {
                    throw new BusinessException(ResultCode.INTERNAL_ERROR);
                }
            }
        }
        return true;
    }

    @Override
    public boolean deleteBlog(Long id) {
        return this.removeById(id);
    }

    @Override
    public BlogInfoVO getBlogInfo(Long id) {
        BlogEntity entity = this.getById(id);
        if (entity == null) {
            throw new BusinessException(ResultCode.ERROR, "查询数据错误");
        } else {
            BlogInfoVO vo = BlogConverter.INSTANCE.toInfoVO(entity);
            List<BlogTagEntity> tagsByBlogId = blogMapper.getTagsByBlogId(id);
            List<String> tagIdList = tagsByBlogId.stream().map(e -> e.getTagId().toString()).toList();
            vo.setTags(tagIdList);

            return vo;
        }
    }

    @Override
    public boolean updateBlog(BlogUpdateDTO dto) {
        BlogEntity entity = BlogConverter.INSTANCE.toEntity(dto);
        return this.updateById(entity);
    }
}
