package cc.onelooker.kaleido.service.movie;

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

    @Test
    void deleteAll() {
        String plexMovieLibraryId = ConfigUtils.getSysConfig("plexMovieLibraryId");
        List<Metadata> metadataList = plexApiService.listCollection(plexMovieLibraryId);
        int size = metadataList.size();
        log.info("总共【{}】条记录待删除", size);
        for (Metadata metadata : metadataList) {
            try {
                plexApiService.deleteCollection(metadata.getRatingKey());
                log.info("{}.【{}】含{}部，删除成功", size--, metadata.getTitle(), metadata.getChildCount());
            } catch (Exception e) {
                log.error("{}.【{}】含{}部，删除发生错误:{}", size--, metadata.getTitle(), metadata.getChildCount(), ExceptionUtil.getMessage(e));
            }
        }
    }

}