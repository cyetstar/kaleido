package cc.onelooker.kaleido.service.movie;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.*;

/**
 * @Author xiadawei
 * @Date 2023-12-22 12:01:00
 * @Description TODO
 */
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieManagerTest {

    @Autowired
    private MovieManager movieManager;

    @Test
    public void syncDoubanWeekly() {
        movieManager.syncDoubanWeekly();
    }

}