package cc.onelooker.kaleido.nfo;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

/**
 * @Author xiadawei
 * @Date 2023-12-19 13:25:00
 * @Description TODO
 */
public class NFOUtilTest {

    @Test
    public void read() throws JAXBException {
        MovieNFO movieNFO = NFOUtil.read(MovieNFO.class, Paths.get("/Users/cyetstar/Downloads"), "movie.nfo");
        assertTrue(StringUtils.isNotEmpty(movieNFO.getDoubanId()));
    }

    @Test
    public void write() throws Exception {
        MovieNFO movieNFO = new MovieNFO();
        movieNFO.setPlot("test");
        NFOUtil.write(movieNFO, MovieNFO.class, Paths.get("/Users/cyetstar/Downloads"), "movie2.nfo");
    }

}