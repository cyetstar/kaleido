package cc.onelooker.kaleido.config;

import cc.onelooker.kaleido.thymeleaf.ExtraDialect;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig {

    @Bean
    public ExtraDialect extraDialect() {
        return new ExtraDialect();
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
