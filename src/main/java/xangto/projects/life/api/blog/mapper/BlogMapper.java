package xangto.projects.life.api.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import xangto.projects.life.api.blog.entity.BlogEntity;

public interface BlogMapper extends BaseMapper<BlogEntity> {
    @Insert("insert into blog_tag set blog_id = #{blogId},tag_id=#{tagId}")
    int saveBlogTag(Long blogId,Long tagId);
}
