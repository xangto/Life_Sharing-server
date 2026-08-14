package xangto.projects.life.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "分页请求参数")
public class PageDTO {
    @Schema(description = "页码", example = "1")
    @Min(1)
    private Long pageNum = 1L;
    @Schema(description = "页大小", example = "10")
    @Min(1)
    private Long pageSize = 10L;

    /** 转为 MyBatis-Plus 分页对象 */
    public <T> Page<T> toPage() {
        return new Page<>(pageNum, pageSize);
    }
}
