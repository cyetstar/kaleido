package cc.onelooker.kaleido;

import com.zjjcnt.common.core.utils.ApplicationContextHelper;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class KaleidoApplication implements CommandLineRunner {

    @Autowired
    private StringEncryptor jasyptStringEncryptor;

    public static void main(String[] args) {
        SpringApplication.run(KaleidoApplication.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setIgnoreUnresolvablePlaceholders(true);
        return c;
    }

    @Override
    public void run(String... args) throws Exception {
        PasswordEncoder passwordEncoder = ApplicationContextHelper.getBean(PasswordEncoder.class);
        String username = jasyptStringEncryptor.encrypt("devoa");
        String password = jasyptStringEncryptor.encrypt("ccecj@0801new");
        System.out.println(username);
        System.out.println(password);
    }
}
