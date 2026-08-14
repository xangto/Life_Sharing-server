package xangto.projects.life.api.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("`blog_tag`")
public class BlogTagEntity {
    private Long blogId;
    private Long tagId;
}
