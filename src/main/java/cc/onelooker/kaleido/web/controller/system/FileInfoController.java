package cc.onelooker.kaleido.web.controller.system;

import com.zjjcnt.common.core.annotation.CacheControl;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.file.FileInfo;
import com.zjjcnt.common.file.FileTransportManager;
import com.zjjcnt.common.file.enums.FileStorageType;
import com.zjjcnt.common.file.enums.ThumbnailSize;
import cc.onelooker.kaleido.convert.system.FileInfoConvert;
import cc.onelooker.kaleido.dto.system.FileInfoDTO;
import cc.onelooker.kaleido.dto.system.req.FileInfoUploadReq;
import cc.onelooker.kaleido.dto.system.resp.FileInfoUploadResp;
import cc.onelooker.kaleido.service.system.FileInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author sjl
 * @date: 2022/9/28 10:05
 * @description:
 */
@RestController
@Api(tags = "文件上传")
@RequestMapping("/fileInfo")
public class FileInfoController extends AbstractCrudController<FileInfoDTO> {
    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private FileTransportManager fileTransportManager;

    @Override
    protected IBaseService<FileInfoDTO> getService() {
        return fileInfoService;
    }

    @ApiOperation("上传文件")
    @PostMapping("upload")
    public CommonResult<FileInfoUploadResp> upload(FileInfoUploadReq req) {
        return super.create(req, FileInfoConvert.INSTANCE::convertToDTO, FileInfoConvert.INSTANCE::convertToUploadResp);
    }

    @ApiOperation("下载文件")
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

    @ApiOperation("删除文件")
    @DeleteMapping(value = "delete")
    public CommonResult<Boolean> delete(String fileId) {
        fileInfoService.deleteByFileId(fileId);
        return CommonResult.success(true);
    }

    @CacheControl(maxAge = 60 * 60 * 24)
    @ApiOperation(value = "访问文件")
    @GetMapping("/{size}/{fileId}")
    public HttpEntity<byte[]> show(@PathVariable String size, @PathVariable String fileId) {
        FileStorageType storageType = fileTransportManager.getStorageType();
        if (FileStorageType.MINIO.equals(storageType)) {
            throw new RuntimeException("minio存储的文件应直接访问其服务提供的地址");
        }
        FileInfo object = fileTransportManager.getObject(fileId, ThumbnailSize.valueOf(StringUtils.upperCase(size)));
        if (object == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(object.getContentType()))
                .body(object.getData());
    }
}
