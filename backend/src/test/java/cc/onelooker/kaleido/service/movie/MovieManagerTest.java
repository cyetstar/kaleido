package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.dto.MovieDoubanWeeklyDTO;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.nfo.UniqueidNFO;
import cc.onelooker.kaleido.service.MovieBasicService;
import cc.onelooker.kaleido.service.MovieDoubanWeeklyService;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.NioFileUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.io.FileUtils;
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
import java.util.List;
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
    private MovieDoubanWeeklyService movieDoubanWeeklyService;

    @Autowired
    private MovieBasicService movieBasicService;

    @Autowired
    private PlexApiService plexApiService;

    @Test
    public void syncDoubanWeekly() {
        String[] filenames = new String[]{"20231215.json", "20231222.json", "20231229.json", "20240105.json", "20240112.json", "20240119.json", "20240126.json"};
        for (String filename : filenames) {
            String baseName = FilenameUtils.getBaseName(filename);
            LocalDate localDate = LocalDate.parse(baseName, DateTimeFormatter.ofPattern("yyyyMMdd"));
            syncDoubanWeekly(localDate.plusDays(1), "/Users/cyetstar/dev/douban/" + filename);
        }
    }

    private List<Subject> listMovieWeeklyFromJSON(String filePath) {
        String text = null;
        try {
            text = FileUtils.readFileToString(Paths.get(filePath).toFile());
        } catch (IOException e) {
        }
        JSONObject jsonObject = JSONObject.parseObject(text);
        if (jsonObject != null) {
            JSONArray jsonArray = jsonObject.getJSONArray("subjects");
            return jsonArray.toJavaList(Subject.class);
        }
        return Lists.newArrayList();
    }

    public void syncDoubanWeekly(LocalDate localDate, String filePath) {
        int dayOfWeekValue = localDate.getDayOfWeek().getValue();
        int days = (dayOfWeekValue > 5 ? dayOfWeekValue : (7 + dayOfWeekValue)) - 5;
        LocalDate minusLocalDate = localDate.minusDays(days);
        String listingDate = minusLocalDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Subject> subjectList = listMovieWeeklyFromJSON(filePath);
        List<String> doubanIdList = Lists.newArrayList();
        for (Subject subject : subjectList) {
            Movie movie = subject.getMovie();
            doubanIdList.add(movie.getId());
            MovieDoubanWeeklyDTO movieDoubanWeeklyDTO = movieDoubanWeeklyService.findByDoubanId(movie.getId());
            if (movieDoubanWeeklyDTO != null) {
                movieDoubanWeeklyDTO.setTop(subject.getRank());
                movieDoubanWeeklyDTO.addListingDetail(listingDate, subject.getRank());
                movieDoubanWeeklyDTO.setStatus(Constants.YES);
                movieDoubanWeeklyService.update(movieDoubanWeeklyDTO);
            } else {
                movieDoubanWeeklyDTO = new MovieDoubanWeeklyDTO();
                movieDoubanWeeklyDTO.setDoubanId(movie.getId());
                movieDoubanWeeklyDTO.setTitle(movie.getTitle());
                movieDoubanWeeklyDTO.setOriginalTitle(movie.getOriginalTitle());
                movieDoubanWeeklyDTO.setYear(movie.getYear());
                movieDoubanWeeklyDTO.addListingDetail(listingDate, subject.getRank());
                movieDoubanWeeklyDTO.setTop(subject.getRank());
                movieDoubanWeeklyDTO.setThumb(movie.getImages().getSmall());
                movieDoubanWeeklyDTO.setStatus(Constants.YES);
                movieDoubanWeeklyService.insert(movieDoubanWeeklyDTO);
            }
        }
        List<MovieDoubanWeeklyDTO> movieDoubanWeeklyDTOList = movieDoubanWeeklyService.list(null);
        for (MovieDoubanWeeklyDTO movieDoubanWeeklyDTO : movieDoubanWeeklyDTOList) {
            if (StringUtils.equals(movieDoubanWeeklyDTO.getStatus(), Constants.YES) && !doubanIdList.contains(movieDoubanWeeklyDTO.getDoubanId())) {
                //未下榜，但不在本期榜单上
                movieDoubanWeeklyDTO.setTop(movieDoubanWeeklyDTO.getBestTop());
                movieDoubanWeeklyDTO.setStatus(Constants.NO);
                movieDoubanWeeklyService.update(movieDoubanWeeklyDTO);
            }
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
                        NioFileUtil.moveDir(s, Paths.get("/Volumes/tmp/big/已存在"));
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

    @Test
    public void fillFilename() {
        MovieBasicDTO param = new MovieBasicDTO();
        param.setIdList(Lists.newArrayList("348195", "348196"));
        List<MovieBasicDTO> movieBasicDTOList = movieBasicService.list(param);
        for (MovieBasicDTO movieBasicDTO : movieBasicDTOList) {
            Metadata metadata = plexApiService.findMetadata(movieBasicDTO.getId());
            if (metadata != null) {
                movieBasicDTO.setFilename(FilenameUtils.getName(metadata.getMedia().getPart().getFile()));
                movieBasicService.update(movieBasicDTO);
            }
        }
    }

}