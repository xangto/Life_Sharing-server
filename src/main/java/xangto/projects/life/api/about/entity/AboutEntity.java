package xangto.projects.life.api.about.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("about")
public class AboutEntity implements Serializable {
    @TableId
    private Integer id;
    private String nameEn;
    private String nameZh;
    private String content;
}
