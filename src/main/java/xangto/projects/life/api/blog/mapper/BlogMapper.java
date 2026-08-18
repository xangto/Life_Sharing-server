package xangto.projects.life.api.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import xangto.projects.life.api.blog.entity.BlogEntity;
import xangto.projects.life.api.blog.entity.BlogTagEntity;

import java.util.List;

public interface BlogMapper extends BaseMapper<BlogEntity> {
    @Insert("insert into blog_tag set blog_id = #{blogId},tag_id=#{tagId}")
    int saveBlogTag(Long blogId, Long tagId);

    @Select("select * from blog_tag where blog_id = #{blogId};")
    List<BlogTagEntity> getTagsByBlogId(Long blogId);
}
