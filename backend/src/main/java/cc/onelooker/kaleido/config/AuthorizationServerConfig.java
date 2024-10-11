package cc.onelooker.kaleido.config;

import com.zjjcnt.common.security.config.BaseAuthorizationServerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author Ye Jianyu
 * @date 2020/11/2
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends BaseAuthorizationServerConfig {

}
