package cc.onelooker.kaleido.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author yejianyu
 * @date 2021-07-06 15:35:00
 */
@Configuration
public class RestConfig {

    private static final long READ_TIMEOUT_SECONDS = 60L;
    private static final long CONNECT_TIMEOUT_SECONDS = 5L;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setReadTimeout(Duration.ofSeconds(READ_TIMEOUT_SECONDS))
                .setConnectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS))
                .build();
    }
}
