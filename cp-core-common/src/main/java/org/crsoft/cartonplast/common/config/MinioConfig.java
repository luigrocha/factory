package org.crsoft.cartonplast.common.config;

import io.minio.MinioClient;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private String endpoint;
    private Integer port;
    private boolean secure;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    @SneakyThrows
    @Bean
    public MinioClient minioClient() {
        System.out.println(accessKey);
        System.out.println(secretKey);
        MinioClient minioClient = MinioClient.builder()
                .credentials(accessKey, secretKey)
                .endpoint(new URL(endpoint))
                .build();
        return minioClient;
    }
}
