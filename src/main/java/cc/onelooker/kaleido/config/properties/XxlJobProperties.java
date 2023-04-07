package cc.onelooker.kaleido.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Hyliu
 * @version 1.0.0
 * @ClassName XxlJobProperties.java
 * @Description TODO
 * @createTime 2022年05月10日 09:15:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {
    /**
     * 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；
     */
    private XxlJobAdminProperties admin;
    /**
     * 执行器通讯TOKEN [选填]：非空时启用；
     */
    private String accessToken;

    private XxlJobExecutorProperties executor;

    /**
     * 是否开启 xxl-job 定时任务调度器
     */
    private boolean enabled;
}
