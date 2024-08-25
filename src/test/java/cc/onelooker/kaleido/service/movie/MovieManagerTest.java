package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.service.MovieManager;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        String[] filenames = new String[]{"20231215.json", "20231222.json", "20231229.json", "20240105.json",
                "20240112.json", "20240119.json", "20240126.json"};
        for (String filename : filenames) {
            String baseName = FilenameUtils.getBaseName(filename);
            LocalDate localDate = LocalDate.parse(baseName, DateTimeFormatter.ofPattern("yyyyMMdd"));
            movieManager.syncDoubanWeekly(localDate.plusDays(1), "/Users/cyetstar/dev/douban/" + filename);
        }
    }

}