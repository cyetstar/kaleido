package cc.onelooker.kaleido.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Hyliu
 * @version 1.0.0
 * @ClassName XxlJobAdminProperties.java
 * @Description TODO
 * @createTime 2022年05月10日 09:24:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "xxl.job.admin")
public class XxlJobAdminProperties {

    private String addresses;
}
