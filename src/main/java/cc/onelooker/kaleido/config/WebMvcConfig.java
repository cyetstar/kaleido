package cc.onelooker.kaleido.config;

import com.zjjcnt.common.core.format.StringDateTimeFormatAnnotationFormatterFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/devProject/list");
        registry.addRedirectViewController("/basic", "/devCustomer/list");
        registry.addRedirectViewController("/organization", "/devDepartment/list");
        registry.addRedirectViewController("/user", "/sysUser/list");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("/error/500");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new StringDateTimeFormatAnnotationFormatterFactory());
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:org/springframework/security/messages_zh_CN");
        return messageSource;
    }

}
