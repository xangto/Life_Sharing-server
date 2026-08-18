package xangto.projects.life.api.blog.vo;

import lombok.Data;
import xangto.projects.life.api.tag.vo.TagVO;

import java.util.List;

@Data
public class BlogInfoVO {
    private String id;
    private String title;
    private String firstPicture;
    private String description;
    private String content;
    private Boolean isPublished;
    private Integer views;
    private Integer words;
    private Integer readTime;
    private Boolean isTop;
    private String categoryId;
    private String categoryName;
    private List<String> tags;
    private List<TagVO> tagList;
    private String userId;
    private String updateTime;
    private String createTime;
}
