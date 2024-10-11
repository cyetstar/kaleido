package cc.onelooker.kaleido.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Hyliu
 * @version 1.0.0
 * @ClassName MinioProperties.java
 * @Description TODO
 * @createTime 2022年02月26日 12:28:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private String endpoint;
    private String bucketName;
    private String accessKey;
    private String secretKey;
}
