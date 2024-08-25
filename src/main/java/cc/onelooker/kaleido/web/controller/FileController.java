package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.dto.req.*;
import cc.onelooker.kaleido.dto.resp.FilePageResp;
import cc.onelooker.kaleido.service.AsyncTaskManager;
import cc.onelooker.kaleido.utils.NioFileUtils;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author cyetstar
 * @Date 2023-12-03 23:39:00
 * @Description TODO
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @GetMapping("page")
    public CommonResult<PageResult<FilePageResp>> page(FilePageReq req, PageParam pageParam) throws IOException {
        List<FilePageResp> records = Files.list(Paths.get(req.getPath())).map(s -> {
            FilePageResp filePageResp = new FilePageResp();
            File file = s.toFile();
            filePageResp.setName(file.getName());
            filePageResp.setPath(s.toString());
            filePageResp.setIsDir(file.isDirectory());
            filePageResp.setLength(file.length());
            filePageResp.setLastModified(DateTimeUtils.parseTimestamp(file.lastModified()));
            MediaType mediaType = MediaTypeFactory.getMediaType(file.getName()).orElse(MediaType.ALL);
            filePageResp.setMediaType(mediaType.toString());
            return filePageResp;
        }).collect(Collectors.toList());
        String orderBy = pageParam.getOrderBy();
        if (StringUtils.isNotEmpty(orderBy)) {
            String[] values = StringUtils.split(orderBy, ":");
            String field = values[0];
            String direction = values[1];
            Comparator<FilePageResp> comparing = null;
            if (StringUtils.equals(field, "name")) {
                comparing = Comparator.comparing(FilePageResp::getName);
            } else if (StringUtils.equals(field, "lastModified")) {
                comparing = Comparator.comparing(FilePageResp::getLastModified);
            } else if (StringUtils.equals(field, "length")) {
                comparing = Comparator.comparing(FilePageResp::getLength);
            }
            if (StringUtils.equals(direction, "DESC")) {
                comparing = comparing.reversed();
            }
            if (comparing != null) {
                records = records.stream().sorted(comparing).collect(Collectors.toList());
            }
        }
        PageResult<FilePageResp> result = PageResult.convert(pageParam.toPage(), records);
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

    @PostMapping("newDirectory")
    public CommonResult<Boolean> newDirectory(@RequestBody FileNewDirectoryReq req) throws IOException {
        Path path = Paths.get(req.getPath());
        if (!Files.isDirectory(path)) {
            return CommonResult.success(false);
        }
        String name = "未命名文件夹";
        String folderName = Files.list(path).map(s -> s.getFileName().toString()).filter(s -> StringUtils.startsWith(s, name)).max(Comparator.naturalOrder()).orElse(null);
        if (StringUtils.isNotEmpty(folderName)) {
            String num = StringUtils.defaultIfEmpty(StringUtils.substring(folderName, 6), "0");
            folderName = name + (Integer.parseInt(num) + 1);
        } else {
            folderName = name;
        }
        Files.createDirectory(path.resolve(folderName));
        return CommonResult.success(true);
    }

    @PostMapping("copyOrCut")
    public CommonResult<Boolean> copyOrCut(@RequestBody FileCopyOrCutReq req) throws IOException {
        for (String pathStr : req.getPathList()) {
            Path path = Paths.get(pathStr);
            if (Files.isDirectory(path) && req.getCopy()) {
                NioFileUtils.copyDir(path, Paths.get(req.getDestPath()));
            } else if (Files.isDirectory(path) && !req.getCopy()) {
                NioFileUtils.moveDir(path, Paths.get(req.getDestPath()));
            } else if (!Files.isDirectory(path) && req.getCopy()) {
                Files.copy(path, Paths.get(req.getDestPath()).resolve(path.getFileName()));
            } else if (!Files.isDirectory(path) && !req.getCopy()) {
                Files.move(path, Paths.get(req.getDestPath()).resolve(path.getFileName()));
            }
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
