package xangto.projects.life.utils;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.PutObjectRequest;
import com.aliyun.sdk.service.oss2.models.PutObjectResult;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class OssClientSingleton {
    private final AliOSSProperties aliOSSProperties;
    private volatile OSSClient ossClient;

    private OSSClient getClient() {
        if (ossClient == null) {
            synchronized (this) {
                if (ossClient == null) {
                    ossClient = OSSClient.newBuilder()
                            .credentialsProvider(new EnvironmentVariableCredentialsProvider())
                            .region(aliOSSProperties.getRegion())
                            .build();
                    log.info("OSS 客户端初始化成功，region: {}", aliOSSProperties.getRegion());

                }
            }
        }
        return ossClient;
    }

    @PreDestroy
    public void destroy() {
        if (ossClient != null) {
            try {
                ossClient.close();
                log.info("OSS 客户端已关闭");
            } catch (Exception e) {
                log.warn("OSS 客户端关闭异常: {}", e.getMessage());
            }
        }
    }

    public String upload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }

        String filename = file.getOriginalFilename();
        String suffix = null;
        if (filename != null) {
            suffix = filename.substring(filename.lastIndexOf("."));
        }
        String newFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss-"))
                + UUID.randomUUID() + suffix;

        OSSClient instance = getClient();
        PutObjectResult result = instance.putObject(PutObjectRequest.newBuilder()
                .bucket(aliOSSProperties.getBucketName())
                .key(newFileName)
                .body(BinaryData.fromBytes(file.getBytes()))
                .build());
        log.info("OSS 上传完成，状态码: {}, 文件名: {}", result.statusCode(), newFileName);
        return String.format("https://%s.%s/%s", aliOSSProperties.getBucketName(), aliOSSProperties.getEndpoint(), newFileName);
    }

}