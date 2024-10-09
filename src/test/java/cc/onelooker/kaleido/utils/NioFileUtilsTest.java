package cc.onelooker.kaleido.utils;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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