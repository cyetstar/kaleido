package cc.onelooker.kaleido.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Hyliu
 * @version 1.0.0
 * @ClassName AliyunOssProperties.java
 * @Description TODO
 * @createTime 2022年02月26日 12:12:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyu.oss")
public class AliyunOssProperties {

    /**
     * 签名有效期
     */
    private AliyunOssPolicyProperties policy;
    /**
     * 上传文件大小
     */
    private Long maxSize;
    /**
     * 回调地址
     */
    private String callback;
    /**
     * Bucket名称
     */
    private String bucketName;
    /**
     * oss对外服务的访问域名
     */
    private String endpoint;
    /**
     * 上传文件夹路径前缀
     */
    private AliyunOssDirProperties dir;
    /**
     * 访问身份验证中用到用户标识
     */
    private String accessKeyId;
    /**
     * 用户用于加密签名字符串和oss用来验证签名字符串的密钥
     */
    private String accessKeySecret;
}
