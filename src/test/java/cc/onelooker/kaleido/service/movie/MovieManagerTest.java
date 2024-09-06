package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.nfo.UniqueidNFO;
import cc.onelooker.kaleido.service.MovieBasicService;
import cc.onelooker.kaleido.service.MovieManager;
import cc.onelooker.kaleido.utils.NioFileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private MovieBasicService movieBasicService;

    @Test
    public void syncDoubanWeekly() {
        String[] filenames = new String[]{"20231215.json", "20231222.json", "20231229.json", "20240105.json", "20240112.json", "20240119.json", "20240126.json"};
        for (String filename : filenames) {
            String baseName = FilenameUtils.getBaseName(filename);
            LocalDate localDate = LocalDate.parse(baseName, DateTimeFormatter.ofPattern("yyyyMMdd"));
            movieManager.syncDoubanWeekly(localDate.plusDays(1), "/Users/cyetstar/dev/douban/" + filename);
        }
    }

    @Test
    public void findExist() throws IOException {
        Files.list(Paths.get("/Volumes/tmp/big/港台大陆")).forEach(s -> {
            try {
                Path nfoPath = Files.find(s, 2, (path, basicFileAttributes) -> {
                    String name = path.getFileName().toString();
                    if (name.endsWith(".nfo")) {
                        return true;
                    }
                    return false;
                }).findFirst().orElse(null);
                UniqueidNFO uniqueidNFO = null;
                if (nfoPath == null) return;
                MovieNFO movieNFO = NFOUtil.read(MovieNFO.class, nfoPath.getParent(), nfoPath.getFileName().toString());
                if (movieNFO != null && movieNFO.getUniqueids() != null) {
                    uniqueidNFO = movieNFO.getUniqueids().stream().filter(u -> StringUtils.equals(u.getType(), "imdb")).findFirst().orElse(null);
                }
                if (uniqueidNFO != null) {
                    MovieBasicDTO movieBasicDTO = movieBasicService.findByImdbId(uniqueidNFO.getValue());
                    if (movieBasicDTO != null) {
                        NioFileUtils.moveDir(s, Paths.get("/Volumes/tmp/big/已存在"));
                        System.out.println(s.toString() + "===> 已存在");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private Pattern pattern = Pattern.compile("(\\d+).*$");

    @Test
    public void testRename() throws IOException {
        Files.list(Paths.get("/Volumes/music/三国演义")).forEach(s -> {
            try {
                String fileName = s.getFileName().toString();
                fileName = FilenameUtils.getBaseName(fileName);
                Matcher matcher = pattern.matcher(fileName);
                if (matcher.find()) {
                    String trackNum = matcher.group(1);
                    String title = StringUtils.trim(StringUtils.removeStart(fileName, trackNum));
                    title = StringUtils.removeStart(title, ".");
                    title = StringUtils.removeEnd(title, ".");
                    Files.move(s, s.resolveSibling(Integer.parseInt(trackNum) + " " + title + ".mp3"));
                }
            } catch (IOException e) {

            }
        });
    }

}