package xangto.projects.life.api.moment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("moment")
public class MomentEntity implements Serializable {
    @TableId
    private Long id;
    private String content;
    private Integer likes;
    private Short isPublished;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
