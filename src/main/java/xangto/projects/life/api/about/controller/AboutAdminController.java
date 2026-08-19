package xangto.projects.life.api.about.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xangto.projects.life.api.about.dto.AboutDTO;
import xangto.projects.life.api.about.entity.AboutEntity;
import xangto.projects.life.api.about.service.AboutService;
import xangto.projects.life.api.about.vo.AboutVO;
import xangto.projects.life.common.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "关于 admin")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/about")
public class AboutAdminController {
    private final AboutService aboutService;

    @Operation(summary = "获取关于信息")
    @GetMapping
    public Result<Map<String, AboutVO>> getAboutInfo() {
        List<AboutEntity> entityList = aboutService.list();
        Map<String, AboutVO> map = new HashMap<>();
        entityList.forEach(e -> {
            AboutVO vo = new AboutVO();
            vo.setId(e.getId());
            vo.setTitle(e.getNameZh());
            vo.setContent(e.getContent());
            map.put(e.getNameEn(), vo);
        });
        return Result.success(map);
    }

    @Operation(summary = "更新关于信息")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public Result<?> updateAboutInfo(@Valid @RequestBody Map<String, AboutDTO> map) {
        if (map == null || map.isEmpty()) {
            return Result.error("传入参数为空");
        }
        ArrayList<AboutEntity> entities = new ArrayList<>();
        map.forEach((k, v) -> {
            AboutEntity entity = new AboutEntity();
            entity.setId(v.getId());
            entity.setContent(v.getContent());
            entity.setNameZh(v.getTitle());
            entity.setNameEn(k);
            entities.add(entity);
        });
        boolean b = aboutService.updateBatchById(entities, entities.size());
        return b ? Result.success() : Result.error();
    }
}
