package xangto.projects.life.api.category.service;

import com.baomidou.mybatisplus.extension.repository.IRepository;
import xangto.projects.life.api.category.dto.CategoryCreateDTO;
import xangto.projects.life.api.category.entity.CategoryEntity;
import xangto.projects.life.api.category.vo.CategoryVO;
import xangto.projects.life.common.PageDTO;
import xangto.projects.life.common.PageVO;

public interface CategoryService extends IRepository<CategoryEntity> {
    boolean createCategory(CategoryCreateDTO dto);

    PageVO<CategoryVO> getCategoryList(PageDTO dto);

    boolean deleteCategory(Long id);
}
