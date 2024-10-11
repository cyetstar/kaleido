package cc.onelooker.kaleido.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author Hyliu
 * @version 1.0.0
 * @ClassName PasswordErrorLockProperties.java
 * @Description TODO
 * @createTime 2021年12月03日 23:56:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth.password-error-lock")
public class PasswordErrorLockProperties {
    /**
     * 是否开启密码错误锁定账号
     */
    private boolean enabled = false;
    /**
     * 密码错误次数
     */
    private int count = 5;

    /**
     * 账号锁定时间
     */
    private Duration duration = Duration.ofSeconds(1800L);
}
