package cc.onelooker.kaleido.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Hyliu
 * @version 1.0.0
 * @ClassName NettyServerProperties.java
 * @Description TODO
 * @createTime 2022年05月09日 14:34:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "netty-server")
public class NettyServerProperties {

    /**
     * 是否开启Netty socket
     */
    private boolean enabled;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 线程队列维护的连接个数
     */
    private Integer backlog;
    /**
     * 设置连接状态行为，保持连接状态
     */
    private Boolean keepAlive;
}
