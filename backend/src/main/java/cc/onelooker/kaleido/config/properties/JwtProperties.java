package cc.onelooker.kaleido.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Hyliu
 * @version 1.0.0
 * @ClassName JwtProperties.java
 * @Description TODO
 * @createTime 2021年12月03日 21:54:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String tokenHeader;
    private String tokenHead;
    private String secret;
    private Long expiration;

}
