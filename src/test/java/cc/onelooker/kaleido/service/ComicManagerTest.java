package cc.onelooker.kaleido.service;

import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author xiadawei
 * @Date 2024-03-25 21:08:00
 * @Description TODO
 */
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ComicManagerTest {

    @Autowired
    private ComicManager comicManager;

    @Test
    public void compressZip() throws IOException {
        Path path = Paths.get("/Users/cyetstar/dev/kaleido/Vol.1.zip");
        ZipUtil.unzip(path.toFile(), Paths.get("/Users/cyetstar/dev/kaleido/Vol.1").toFile());
//        comicManager.compressZip(path);
    }


}