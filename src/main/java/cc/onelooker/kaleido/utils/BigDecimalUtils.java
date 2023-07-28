package cc.onelooker.kaleido.utils;

import java.math.BigDecimal;

/**
 * @Author xiadawei
 * @Date 2023-07-26 17:10:00
 * @Description TODO
 */
public class BigDecimalUtils {

    public static String divide(String a, String b, int scale) {
        BigDecimal abd = new BigDecimal(a);
        BigDecimal bbd = new BigDecimal(b);
        BigDecimal result = abd.divide(bbd, scale, BigDecimal.ROUND_DOWN);
        return result.toPlainString();
    }
}
