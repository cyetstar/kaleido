package cc.onelooker.kaleido;

import org.apache.commons.lang3.RegExUtils;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author xiadawei
 * @Date 2023-12-19 13:25:00
 * @Description TODO
 */
public class TempTest {

    @Test
    public void removePattern() {
        String title = "Creepin' In";
        String simpleTitle = RegExUtils.removePattern(title, "'|’");
        assertEquals("Creepin In", simpleTitle);
        title = "Loretta (live, 2004)";
        simpleTitle = RegExUtils.removePattern(title, "\\(.+\\)");
        assertEquals("Loretta ", simpleTitle);
    }

    @Test
    public void getSeasonEpisodeNumber() {
        Pattern pattern = Pattern.compile("([sS]_?(\\d+))?[eE][pP]?_?(\\d+)");
        Matcher matcher = pattern.matcher("[不毛地带].[TVBT]Fumo.Chitai_EP15_ChineseSubbed.rmvb");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
        }
    }

}