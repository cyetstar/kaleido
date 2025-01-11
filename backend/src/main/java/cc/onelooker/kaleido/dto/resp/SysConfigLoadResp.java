package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 系统配置表响应对象
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("系统配置表响应对象")
public class SysConfigLoadResp {

    private String movieImportPath;
    private String musicImportPath;
    private String tvshowImportPath;
    private String comicImportPath;
}
