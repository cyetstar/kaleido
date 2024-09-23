package cc.onelooker.kaleido.utils;

import cc.onelooker.kaleido.dto.MovieThreadDTO;
import cc.onelooker.kaleido.dto.MovieThreadFilenameDTO;
import cc.onelooker.kaleido.dto.PTThreadDTO;
import cc.onelooker.kaleido.enums.ThreadStatus;
import cc.onelooker.kaleido.service.MovieThreadFilenameService;
import cc.onelooker.kaleido.service.MovieThreadService;
import cc.onelooker.kaleido.service.PTThreadService;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author xiadawei
 * @Date 2023-12-25 19:38:00
 * @Description TODO
 */
public class NioFileUtilsTest {

    @Test
    public void moveDir() throws IOException {
        Path path1 = Paths.get("/Users/cyetstar/dev/kaleido/path1");
        Path path2 = Paths.get("/Users/cyetstar/dev/kaleido/path2");
        NioFileUtils.moveDir(path1, path2);
    }

    @Test
    public void renameDir() throws IOException {
        Path path1 = Paths.get("/Users/cyetstar/dev/kaleido/path1");
        Path path2 = Paths.get("/Users/cyetstar/dev/kaleido/path3");
        NioFileUtils.renameDir(path1, path2);
    }

    @Test
    public void dirIsEmpty() throws IOException {
        Path path = Paths.get("/Volumes/comic/library");
        Files.list(path).forEach(s -> {
            try {
                if (Files.isDirectory(s) && Files.list(s).count() == 0) {
                    System.out.println(s);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}