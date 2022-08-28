package cc.onelooker.kaleido.config;

import cc.onelooker.kaleido.security.CaptchaFilter;
import cc.onelooker.kaleido.security.CustomAuthenticationFailureHandler;
import cc.onelooker.kaleido.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CaptchaFilter captchaFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/error", "/captcha", "/sysUser/register", "/static/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin().failureHandler(customAuthenticationFailureHandler)
                .defaultSuccessUrl("/").loginPage("/login").permitAll(); // 自定义的登录页面
        http.csrf().disable();//关闭CSRF保护即可。
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
