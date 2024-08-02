package cc.onelooker.kaleido.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.xml.bind.JAXBException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author xiadawei
 * @Date 2024-02-01 09:42:00
 * @Description TODO
 */
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KaleidoUtilsTest {

    @Test
    public void getMovieFolder() throws JAXBException, URISyntaxException {
        Path filePath = KaleidoUtils.getMoviePath("/Volumes/movie/1970s/你还好吗? (1976)/俾鬼抓.Ghost.Snatchers.1986.DVD5.x264.2AAC.HALFCD-TLF.mkv");
        URI uri = new URI(StringUtils.replace(filePath.getParent().toString(), "?", "%3F"));

        Assertions.assertTrue(Files.exists(Paths.get(uri).resolve("movie.nfo")));
//        MovieNFO movieNFO = NFOUtil.read(Paths.get(decodePath), "movie.nfo");
//        Assertions.assertNotNull(movieNFO);
    }

}