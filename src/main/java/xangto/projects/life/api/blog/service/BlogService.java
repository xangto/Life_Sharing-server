package xangto.projects.life.api.blog.service;

import com.baomidou.mybatisplus.extension.repository.IRepository;
import xangto.projects.life.api.blog.dto.BlogCreateDTO;
import xangto.projects.life.api.blog.dto.BlogListDTO;
import xangto.projects.life.api.blog.dto.BlogUpdateDTO;
import xangto.projects.life.api.blog.entity.BlogEntity;
import xangto.projects.life.api.blog.vo.BlogInfoVO;
import xangto.projects.life.api.blog.vo.BlogVO;
import xangto.projects.life.common.PageVO;


public interface BlogService extends IRepository<BlogEntity> {
    PageVO<BlogVO> getBlogList(BlogListDTO dto);

    boolean createBlog(BlogCreateDTO dto, Long userId);

    boolean deleteBlog(Long id);

    BlogInfoVO getBlogInfo(Long id);

    boolean updateBlog(BlogUpdateDTO dto);
}
