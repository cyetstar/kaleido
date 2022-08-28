package cc.onelooker.kaleido.thymeleaf;

import com.zjjcnt.common.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by cyetstar on 17/5/7.
 */
public class DateTimes {

    public String format(String value) {
        String pattern = "yyyy-MM-dd";
        if (StringUtils.length(value) == 14) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        return format(value, pattern);
    }

    public String format(String value, String pattern) {
        try {
            if (StringUtils.length(value) == 14) {
                return DateTimeUtils.formatDateTime(value, pattern);
            }
            return DateTimeUtils.formatDate(value, pattern);
        } catch (Exception e) {
            return value;
        }
    }

}
