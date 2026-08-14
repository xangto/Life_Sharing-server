package xangto.projects.life.api.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "blog")
@Data
public class BlogEntity implements Serializable {
    @TableId
    private Long id;
    private String title;
    private String firstPicture;
    private String description;
    private String content;
    private Boolean isPublished;
    private Integer views;
    private Integer words;
    private Integer readTime;
    private Boolean isTop;
    private Long categoryId;
    private Long userId;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}