package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.dto.FileInfoDTO;
import com.zjjcnt.common.core.dto.CommonResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.file.FileInfo;
import com.zjjcnt.common.file.FileTransportManager;
import com.zjjcnt.common.file.enums.FileStorageType;
import com.zjjcnt.common.file.enums.ThumbnailSize;
import cc.onelooker.kaleido.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 文件信息前端控制器
 *
 * @author xiadawei
 * @date 2022-05-31 00:27:23
 */

@Controller
@RequestMapping("/fileInfo")
public class FileInfoController extends CrudController<Long, FileInfoDTO> {

    @Autowired
    private FileTransportManager fileTransportManager;

    @Autowired
    private FileInfoService fileInfoService;

    @Override
    protected IBaseService getService() {
        return fileInfoService;
    }

    @GetMapping(value = "upload")
    public String upload(@ModelAttribute FileInfoDTO fileInfoDTO, Model model) {
        return getViewPath("upload");
    }

    @ResponseBody
    @PostMapping(value = "upload.json", produces = "application/json")
    public HttpEntity<CommonResult> setPassword(FileInfoDTO fileInfoDTO) {
        return getHttpEntity(() -> {
            fileInfoService.insert(fileInfoDTO);
            return ResponseEntity.ok(CommonResult.success(true));
        });
    }

    @GetMapping(value = "download")
    public HttpEntity<byte[]> download(String fileId) throws UnsupportedEncodingException {
        FileStorageType storageType = fileTransportManager.getStorageType();
        if (FileStorageType.MINIO.equals(storageType)) {
            throw new RuntimeException("minio存储的文件应直接访问其服务提供的地址");
        }
        FileInfo object = fileTransportManager.getObject(fileId, ThumbnailSize.ORIGINAL);
        if (object == null) {
            return ResponseEntity.notFound().build();
        }
        String filename = URLEncoder.encode(object.getFilename(), "UTF-8");
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType(object.getContentType()))
                .body(object.getData());
    }

    @GetMapping(value = "show")
    public HttpEntity<byte[]> show(String fileId) throws UnsupportedEncodingException {
        FileStorageType storageType = fileTransportManager.getStorageType();
        if (FileStorageType.MINIO.equals(storageType)) {
            throw new RuntimeException("minio存储的文件应直接访问其服务提供的地址");
        }
        FileInfo object = fileTransportManager.getObject(fileId, ThumbnailSize.ORIGINAL);
        if (object == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(object.getContentType()))
                .body(object.getData());
    }

}