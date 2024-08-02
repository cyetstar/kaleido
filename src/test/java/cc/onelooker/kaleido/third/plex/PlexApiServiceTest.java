package cc.onelooker.kaleido.third.plex;

import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author xiadawei
 * @Date 2024-01-09 15:24:00
 * @Description TODO
 */
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlexApiServiceTest {

    @Autowired
    private PlexApiService plexApiService;

    @Test
    public void findInvalidFolder() {
        String plexMovieLibraryId = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryId);
        List<Metadata> metadataList = plexApiService.listMovie(plexMovieLibraryId);
        String movieLibraryPath = "/Volumes/movie";
        List<String> pathList = null;
        try (Stream<Path> paths = Files.list(Paths.get(movieLibraryPath))) {
            pathList = paths.map(s -> s.getFileName().toString()).collect(Collectors.toList());
        } catch (Exception e) {
        }
        List<String> plexPathList = Lists.newArrayList();
        for (Metadata metadata : metadataList) {
            Metadata meta = plexApiService.findMovieById(metadata.getRatingKey());
            Path filePath = KaleidoUtils.getMoviePath(meta.getMedia().getPart().getFile());
            plexPathList.add(FilenameUtils.getBaseName(filePath.getParent().toString()));
        }
        Collection<String> subtractList = CollectionUtils.subtract(pathList, plexPathList);
        for (String subtractPath : subtractList) {
            log.info("无效文件夹：{}", subtractPath);
        }
    }

}