package cc.onelooker.kaleido.dto.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Hyliu
 * @version 1.0.0
 * @ClassName SysDTO.java
 * @Description TODO
 * @createTime 2022年05月26日 17:13:00
 */
@Data
public class SysDTO {

    /**
     * 服务器名称
     */
    @ApiModelProperty("服务器名称")
    private String computerName;

    /**
     * 服务器Ip
     */
    @ApiModelProperty("服务器Ip")
    private String computerIp;

    /**
     * 项目路径
     */
    @ApiModelProperty("项目路径")
    private String userDir;

    /**
     * 操作系统
     */
    @ApiModelProperty("操作系统")
    private String osName;

    /**
     * 系统架构
     */
    @ApiModelProperty("系统架构")
    private String osArch;
}
