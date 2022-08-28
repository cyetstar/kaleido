package cc.onelooker.kaleido.config;

import cc.onelooker.kaleido.utils.DevoaConstants;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.google.common.collect.Maps;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @Author xiadawei
 * @Date 2022-08-20 19:04:00
 * @Description TODO
 */
@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean kaptchaServletRegistrationBean() {

        Map<String, String> initParameters = Maps.newHashMap();
        initParameters.put("kaptcha.session.key", DevoaConstants.KAPTCHA_CODE);
        initParameters.put("kaptcha.session.date", DevoaConstants.KAPTCHA_TIME);
        initParameters.put("kaptcha.textproducer.char.length", "4");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new KaptchaServlet(), "/captcha");
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }
}
