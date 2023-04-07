package cc.onelooker.kaleido.config;

import com.zjjcnt.common.security.config.BaseWebSecurityConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @Author xiadawei
 * @Date 2022-03-21 14:41:00
 * @Description TODO
 */
@Configurable
@EnableWebSecurity
public class WebSecurityConfig extends BaseWebSecurityConfig {

}
