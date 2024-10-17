package cc.onelooker.kaleido.third.plex;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2024-01-09 15:24:00
 * @Description TODO
 */
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlexApiServiceTest {

    @Autowired
    private PlexApiService plexApiService;

    @Test
    public void listMetadataChildren() {
        plexApiService.listMetadataChildren("348451");

    }

}