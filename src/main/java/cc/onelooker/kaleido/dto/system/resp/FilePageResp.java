package cc.onelooker.kaleido.dto.system.resp;

import cc.onelooker.kaleido.utils.KaleidoUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        return KaleidoUtils.getFormatSize(length);
    }


}
