package cc.onelooker.kaleido.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @Author xiadawei
 * @Date 2024-01-04 16:37:00
 * @Description TODO
 */
public class DateTimeUtil {

    public static String formatDateTime(String value) {
        try {
            Date date = DateUtils.parseDate(value, "yyyy-MM-dd HH:mm:ss");
            return DateFormatUtils.format(date, "yyyyMMddHHmmss");
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatDate(String value) {
        try {
            Date date = DateUtils.parseDate(value, "yyyy-MM-dd");
            return DateFormatUtils.format(date, "yyyyMMdd");
        } catch (ParseException e) {
            return null;
        }
    }

}
