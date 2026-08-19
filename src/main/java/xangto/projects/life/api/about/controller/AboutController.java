package xangto.projects.life.api.about.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xangto.projects.life.api.about.entity.AboutEntity;
import xangto.projects.life.api.about.service.AboutService;
import xangto.projects.life.api.about.vo.AboutVO;
import xangto.projects.life.common.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "关于")
@RequiredArgsConstructor
@RestController
@RequestMapping("/about")
public class AboutController {
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
}
