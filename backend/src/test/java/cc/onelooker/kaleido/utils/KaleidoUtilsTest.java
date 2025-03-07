package cc.onelooker.kaleido.utils;

import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
public class KaleidoUtilsTest {

    @Test
    public void getMovieFolder() throws JAXBException, URISyntaxException {
        Path filePath = KaleidoUtil.getMoviePath(
                "/Volumes/movie/1970s/你还好吗? (1976)/俾鬼抓.Ghost.Snatchers.1986.DVD5.x264.2AAC.HALFCD-TLF.mkv");
        URI uri = new URI(StringUtils.replace(filePath.getParent().toString(), "?", "%3F"));

        Assertions.assertTrue(Files.exists(Paths.get(uri).resolve("movie.nfo")));
        // MovieNFO movieNFO = NFOUtil.read(Paths.get(decodePath), "movie.nfo");
        // Assertions.assertNotNull(movieNFO);
    }

    @Test
    public void parseEpisodeIndex() {
        String filename = "The Sound of Your Heart S01E04 1080p NF WEB-DL DDP2.0 x264-ARiN.mkv";
        System.out.println(KaleidoUtil.parseSeasonIndex(filename, 1));
        System.out.println(KaleidoUtil.parseEpisodeIndex(filename));
    }

    @Test
    public void genMusicFolder() {

    }

}