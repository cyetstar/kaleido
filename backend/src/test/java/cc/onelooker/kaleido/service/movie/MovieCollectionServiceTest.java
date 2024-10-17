package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MovieCollectionService;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-12-21 14:42:00
 * @Description TODO
 */
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieCollectionServiceTest {

    @Autowired
    private MovieCollectionService movieCollectionService;

    @Autowired
    private PlexApiService plexApiService;

}