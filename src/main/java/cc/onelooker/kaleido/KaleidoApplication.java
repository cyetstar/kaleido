package cc.onelooker.kaleido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableAsync
@EnableTransactionManagement
@SpringBootApplication
public class KaleidoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaleidoApplication.class, args);
    }

}
