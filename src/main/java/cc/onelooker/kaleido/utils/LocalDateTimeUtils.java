package cc.onelooker.kaleido.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Author cyetstar
 * @Date 2023-11-15 20:46:00
 * @Description TODO
 */
public class LocalDateTimeUtils {

    private static final String PATTERN = "yyyyMMddHHmmss";

    public static LocalDateTime parseToLocalDateTime(String str) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN);
        return LocalDateTime.parse(str, dateTimeFormatter);
    }

    public static LocalDateTime parseToLocalDateTime(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    public static boolean isBefore(String leftStr, String rightStr) {
        return parseToLocalDateTime(leftStr).isBefore(parseToLocalDateTime(rightStr));
    }

    public static boolean isBefore(Long leftLong, String rightStr) {
        return parseToLocalDateTime(leftLong).isBefore(parseToLocalDateTime(rightStr));
    }

    public static boolean isBefore(String leftStr, Long rightLong) {
        return parseToLocalDateTime(leftStr).isBefore(parseToLocalDateTime(rightLong));
    }

    public static boolean isBefore(Long leftLong, Long rightLong) {
        return parseToLocalDateTime(leftLong).isBefore(parseToLocalDateTime(rightLong));
    }

    public static boolean isAfter(String leftStr, String rightStr) {
        return parseToLocalDateTime(leftStr).isAfter(parseToLocalDateTime(rightStr));
    }

    public static boolean isAfter(Long leftLong, String rightStr) {
        if (leftLong == null && StringUtils.isEmpty(rightStr)) {
            return true;
        }
        if (leftLong == null && StringUtils.isNotEmpty(rightStr)) {
            return false;
        }
        if (leftLong != null && StringUtils.isEmpty(rightStr)) {
            return true;
        }
        return parseToLocalDateTime(leftLong).isAfter(parseToLocalDateTime(rightStr));
    }

    public static boolean isAfter(String leftStr, Long rightLong) {
        return parseToLocalDateTime(leftStr).isAfter(parseToLocalDateTime(rightLong));
    }

    public static boolean isAfter(Long leftLong, Long rightLong) {
        return parseToLocalDateTime(leftLong).isAfter(parseToLocalDateTime(rightLong));
    }
}
