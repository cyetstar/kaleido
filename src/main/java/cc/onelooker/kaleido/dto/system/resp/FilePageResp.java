package cc.onelooker.kaleido.dto.system.resp;

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
        if (length == null) {
            return StringUtils.EMPTY;
        }
        return getFormatSize(length);
    }

    private String getFormatSize(long size) {
        double kiloByte = (double) size / 1024;
        if (kiloByte < 1) {
            return "0KB";
        }
        //计算kiloByte
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, RoundingMode.HALF_UP).toPlainString() + "KB";
        }
        //计算megaByte
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, RoundingMode.HALF_UP).toPlainString() + "MB";
        }
        //计算gigaByte
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, RoundingMode.HALF_UP).toPlainString() + "GB";
        }
        //计算TB
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, RoundingMode.HALF_UP).toPlainString() + "TB";
    }
}
