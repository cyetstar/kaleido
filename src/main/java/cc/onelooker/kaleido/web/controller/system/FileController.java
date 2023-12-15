package cc.onelooker.kaleido.web.controller.system;

import cc.onelooker.kaleido.dto.system.req.FileMoveReq;
import cc.onelooker.kaleido.dto.system.req.FileRenameReq;
import cc.onelooker.kaleido.dto.system.resp.FileListResp;
import cc.onelooker.kaleido.service.AsyncTaskManager;
import cc.onelooker.kaleido.utils.NioFileUtils;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.util.DateTimeUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xiadawei
 * @Date 2023-12-03 23:39:00
 * @Description TODO
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @GetMapping("list")
    public CommonResult<List<FileListResp>> list(String path) throws IOException {
        List<FileListResp> result = Files.list(Paths.get(path)).map(s -> {
            FileListResp fileListResp = new FileListResp();
            File file = s.toFile();
            fileListResp.setName(file.getName());
            fileListResp.setPath(s.toString());
            fileListResp.setIsDir(file.isDirectory());
            fileListResp.setLength(file.length());
            fileListResp.setLastModified(DateTimeUtils.parseTimestamp(file.lastModified()));
            return fileListResp;
        }).sorted((Comparator.comparing(FileListResp::getLastModified).reversed())).collect(Collectors.toList());
        return CommonResult.success(result);
    }

    @DeleteMapping("delete")
    public CommonResult<Boolean> delete(@RequestBody String[] path) throws IOException {
        for (String s : path) {
            NioFileUtils.deleteIfExists(Paths.get(s));
        }
        return CommonResult.success(true);
    }

    @PostMapping("rename")
    public CommonResult<Boolean> rename(@RequestBody FileRenameReq req) throws IOException {
        if (StringUtils.equals(req.getPath(), req.getNewPath())) {
            return CommonResult.success(true);
        }
        if (Files.isDirectory(Paths.get(req.getPath()))) {
            NioFileUtils.renameDir(Paths.get(req.getPath()), Paths.get(req.getNewPath()));
        } else {
            Files.move(Paths.get(req.getPath()), Paths.get(req.getNewPath()));
        }
        return CommonResult.success(true);
    }

    @PostMapping("move")
    public CommonResult<Boolean> move(@RequestBody FileMoveReq req) throws IOException {
        asyncTaskManager.moveFile(req.getPathList(), req.getDestPath());
        return CommonResult.success(true);
    }

    @GetMapping("open")
    @ApiOperation(value = "打开文件")
    public HttpEntity<byte[]> open(String path) throws IOException {
        File file = Paths.get(path).toFile();
        String filename = file.getName();
        MediaType mediaType = MediaTypeFactory.getMediaType(filename).orElse(MediaType.TEXT_PLAIN);
        if (MediaType.parseMediaType("text/*").isCompatibleWith(mediaType)) {
            mediaType = new MediaType(mediaType, StandardCharsets.UTF_8);
        }
        return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + filename).contentType(mediaType).body(FileUtils.readFileToByteArray(file));
    }
}
