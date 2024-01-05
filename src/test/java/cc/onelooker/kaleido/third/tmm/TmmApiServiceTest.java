package cc.onelooker.kaleido.third.tmm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
        tmmApiService.setUrl("http://127.0.0.1:6000");
        Movie movie = tmmApiService.findMovie("1292052");
        Assertions.assertEquals(movie.getTitle(),"肖申克的救赎");
        Assertions.assertEquals(movie.getImdbId(),"tt0111161");
    }

    @Test
    public void findDoulist() {
        tmmApiService.setUrl("http://127.0.0.1:6000");
        Doulist doulist = tmmApiService.findDoulist("136414497");
        Assertions.assertEquals(doulist.getTitle(),"想给妈妈看的电影");
    }

    @Test
    public void listDoulistMovie() {
        tmmApiService.setUrl("http://127.0.0.1:6000");
        List<Movie> movieList = tmmApiService.listDoulistMovie("136414497", 0);
        Assertions.assertNotNull(movieList.get(0).getTitle());
    }

}