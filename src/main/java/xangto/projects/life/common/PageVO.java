package xangto.projects.life.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Schema(description = "分页查询")
@EqualsAndHashCode(callSuper = false)
@Data
public class PageVO<T> extends PageDTO {
    @Schema(description = "总数")
    private Long total;
    @Schema(description = "总页数")
    private Long pages;
    @Schema(description = "数据")
    private List<T> records;

    /**
     * 从 MyBatis-Plus 分页结果构建 PageVO
     */
    public static <T> PageVO<T> from(IPage<?> page, List<T> rows) {
        PageVO<T> vo = new PageVO<>();
        vo.setPageNum(page.getCurrent());
        vo.setPageSize(page.getSize());
        vo.setPages(page.getPages());
        vo.setTotal(page.getTotal());
        vo.setRecords(rows);
        return vo;
    }
}
