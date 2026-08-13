package xangto.projects.life.api.tag.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("tag")
public class TagEntity implements Serializable {
    @TableId
    private Long id;
    private String name;
    private String color;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
