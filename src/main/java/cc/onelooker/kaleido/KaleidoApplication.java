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
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableScheduling
@EnableAsync
@EnableTransactionManagement
@SpringBootApplication
public class KaleidoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaleidoApplication.class, args);
    }

}
