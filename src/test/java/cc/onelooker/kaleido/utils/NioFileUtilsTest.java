package cc.onelooker.kaleido.utils;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @Author xiadawei
 * @Date 2023-12-25 19:38:00
 * @Description TODO
 */
public class NioFileUtilsTest {

    @Test
    public void move() throws IOException {
        Path source = Paths.get("/Users/cyetstar/dev/kaleido/a");
        Path target = Paths.get("/Users/cyetstar/dev/kaleido/b");
        NioFileUtils.moveDir(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

}