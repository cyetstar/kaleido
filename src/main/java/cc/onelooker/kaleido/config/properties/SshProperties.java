package cc.onelooker.kaleido.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Hyliu
 * @version 1.0.0
 * @ClassName SshProperties.java
 * @Description TODO
 * @createTime 2022年03月28日 09:59:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "ssh")
public class SshProperties {
    private String user;
    private String password;
    private String host;
    private Integer port;
    private Integer localPort;
    private String remoteHost;
    private Integer remotePort;
    private Boolean enabled;
}
