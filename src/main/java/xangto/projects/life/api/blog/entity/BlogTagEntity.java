package xangto.projects.life.api.blog.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlogTagEntity implements Serializable {
    private Long blogId;
    private Long tagId;
}
