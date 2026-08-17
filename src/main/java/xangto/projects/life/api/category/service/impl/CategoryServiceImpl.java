package xangto.projects.life.api.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xangto.projects.life.api.blog.entity.BlogEntity;
import xangto.projects.life.api.blog.service.BlogService;
import xangto.projects.life.api.category.dto.CategoryCreateDTO;
import xangto.projects.life.api.category.dto.CategoryUpdateDTO;
import xangto.projects.life.api.category.entity.CategoryEntity;
import xangto.projects.life.api.category.mapper.CategoryMapper;
import xangto.projects.life.api.category.service.CategoryService;
import xangto.projects.life.api.category.vo.CategoryVO;
import xangto.projects.life.common.BusinessException;
import xangto.projects.life.common.PageDTO;
import xangto.projects.life.common.PageVO;
import xangto.projects.life.common.ResultCode;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl extends CrudRepository<CategoryMapper, CategoryEntity> implements CategoryService {
    private final BlogService blogService;

    @Override
    public boolean createCategory(CategoryCreateDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        return this.save(entity);
    }

    @Override
    public PageVO<CategoryVO> getCategoryList(PageDTO dto) {
        Page<CategoryEntity> page = this.page(dto.toPage());
        List<CategoryEntity> list = page.getRecords();
        List<CategoryVO> voList = list.stream().map(e -> {
            CategoryVO vo = new CategoryVO();
            vo.setId(e.getId());
            vo.setName(e.getName());
            return vo;
        }).toList();
        return PageVO.from(page, voList);
    }

    @Override
    public boolean updateCategory(CategoryUpdateDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        entity.setId(dto.getId());
        return this.updateById(entity);
    }

    @Override
    public boolean deleteCategory(Long id) {
        LambdaQueryWrapper<BlogEntity> wrapper = new LambdaQueryWrapper<BlogEntity>().eq(BlogEntity::getCategoryId, id);
        long count = blogService.count(wrapper);
        if (count > 0) {
            throw new BusinessException(ResultCode.ERROR, "有文章关联该分类");
        }
        return this.removeById(id);
    }
}
