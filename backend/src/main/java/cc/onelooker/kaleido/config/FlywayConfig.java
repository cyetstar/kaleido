package cc.onelooker.kaleido.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author xiadawei
 * @Date 2024-09-05 19:33:00
 * @Description TODO
 */
@Configuration
public class FlywayConfig {

    @Value("${spring.flyway.enabled:true}")
    private boolean enabled;

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        if (enabled) {
            flyway.migrate();
        }
        return flyway;
    }
}
