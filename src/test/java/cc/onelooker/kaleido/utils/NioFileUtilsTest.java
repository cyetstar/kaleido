package cc.onelooker.kaleido.utils;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

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

    @Test
    public void test() throws IOException {
        Path path = Paths.get("/Users/cyetstar/dev/kaleido/source/ABC (1900)");
        Path sourcePath = Paths.get("/Users/cyetstar/dev/kaleido/source");
        Path targetPath = Paths.get("/Users/cyetstar/dev/kaleido/target");
        String folderName = "ABC (1900)";
        Path folderPath = targetPath.resolve(folderName);
        if (Files.notExists(folderPath)) {
            Files.createDirectory(folderPath);
        }
        //将主文件移动到文件夹
        Files.move(path, folderPath.resolve(path.getFileName()));
        //如果存在额外文件夹也移动
        Path extraPath = path.getParent().resolve("Other");
        if (Files.exists(extraPath)) {
            NioFileUtils.moveDir(extraPath, folderPath, StandardCopyOption.REPLACE_EXISTING);
        }
        //删除父文件夹
        Path parent = path.getParent();
        if (!parent.equals(sourcePath) && Objects.requireNonNull(parent.toFile().list()).length == 0) {
            NioFileUtils.deleteIfExists(parent);
        }
    }

}