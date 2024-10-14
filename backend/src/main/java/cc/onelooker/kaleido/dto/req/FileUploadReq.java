package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author cyetstar
 * @Date 2023-12-04 23:38:00
 * @Description TODO
 */
@Data
public class FileUploadReq {

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("文件")
    private MultipartFile file;

}
