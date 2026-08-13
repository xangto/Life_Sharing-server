package xangto.projects.life.api.category.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("category")
public class CategoryEntity implements Serializable {
    @TableId
    private Long id;
    private String name;
    private String createTime;
}
