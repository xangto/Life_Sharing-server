package xangto.projects.life.api.upload;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xangto.projects.life.common.Result;
import xangto.projects.life.utils.OssClientSingleton;

import java.io.IOException;

@Tag(name = "文件管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/file")
public class UploadController {
    private final OssClientSingleton ossClientSingleton;

    @Operation(summary = "文件上传")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String url = ossClientSingleton.upload(file);
        return Result.success(url);
    }
}
