package cc.onelooker.kaleido.third.tmm;

import cc.onelooker.kaleido.nfo.ComicInfoNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.utils.LogUtil;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2024-01-04 14:53:00
 * @Description TODO
 */
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TmmApiServiceTest {

    @Autowired
    private TmmApiService tmmApiService;

    @Test
    public void findMovie() {
        Logger logger = LogUtil.getLogger(TmmApiServiceTest.class);
        logger.info("[start]");
        logger.debug("[start]");
        logger.trace("[start]");
        logger.warn("[start]");
        logger.error("[start]");

        ThreadUtil.sleep(5000L);
        logger = LogUtil.getLogger(TmmApiServiceTest.class);
//        tmmApiService.setUrl("http://127.0.0.1:6000");
//        Movie movie = tmmApiService.findMovie("1292052", null, null);
//        Assertions.assertEquals(movie.getTitle(), "肖申克的救赎");
//        Assertions.assertEquals(movie.getImdbId(), "tt0111161");
        logger.debug("[start2]");
        logger.trace("[start2]");
        logger.warn("[start2]");
        logger.error("[start2]");
    }

    @Test
    public void findDoulist() {
        Doulist doulist = tmmApiService.findDoulist("136414497");
        Assertions.assertEquals(doulist.getTitle(), "想给妈妈看的电影");
    }

    @Test
    public void listDoulistMovie() {
        List<Movie> movieList = tmmApiService.listDoulistMovie("136414497", 0);
        Assertions.assertNotNull(movieList.get(0).getTitle());
    }

    @Test
    public void findComic() throws Exception {
        Comic comic = tmmApiService.findComic("565");
        String folder = String.format("%S [%S]", comic.getSeries(), comic.getAuthors());
        System.out.println(folder);
        ComicInfoNFO comicInfoNFO = NFOUtil.toComicInfoNFO(comic);
        folder = String.format("%S [%S]", comicInfoNFO.getSeries(), comicInfoNFO.getAuthors());
        System.out.println(folder);
    }

}