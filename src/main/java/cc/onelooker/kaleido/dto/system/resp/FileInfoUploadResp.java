package cc.onelooker.kaleido.dto.system.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2022-10-02 18:43:00
 * @Description TODO
 */
@Data
@ApiModel("文件对象")
public class FileInfoUploadResp {

    @ApiModelProperty("文件id")
    private String fileId;

    @ApiModelProperty("文件名")
    private String filename;

    @ApiModelProperty("文件类型")
    private String contentType;

    @ApiModelProperty("文件大小")
    private Long size;
}
