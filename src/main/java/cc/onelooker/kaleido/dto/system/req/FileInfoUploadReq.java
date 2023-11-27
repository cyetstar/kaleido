package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author cyetstar
 * @Date 2022-10-02 18:40:00
 * @Description TODO
 */
@Data
@ApiModel("文件上传对象")
public class FileInfoUploadReq {

    @ApiModelProperty("业务id")
    private String bizId;

    @ApiModelProperty("业务表名")
    private String bizTable;

    @ApiModelProperty("文件业务类型")
    private String bizType;

    @ApiModelProperty("文件")
    private MultipartFile file;
}
