package cc.onelooker.kaleido.dto.resp;

import cc.onelooker.kaleido.utils.KaleidoUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-12-03 23:42:00
 * @Description TODO
 */
@Data
public class FilePageResp {

    private String name;

    private String path;

    private Boolean isDir;

    private Long length;

    @StringDateTimeFormat
    private String lastModified;

    private String mediaType;

    @JsonProperty
    public String lengthLabel() {
        return KaleidoUtil.getFormatSize(length);
    }


}
