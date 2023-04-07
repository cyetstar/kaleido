package cc.onelooker.kaleido.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Hyliu
 * @version 1.0.0
 * @ClassName CaffeineProperties.java
 * @Description TODO
 * @createTime 2022年08月23日 11:07:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "caffeine.config")
public class CaffeineProperties {

    private Long expireAfterWrite;

    private TimeUnit timeUnit;

    private Long maximumSize;

    private Boolean enabled;
}
