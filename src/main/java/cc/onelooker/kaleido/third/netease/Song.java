package cc.onelooker.kaleido.third.netease;

import cc.onelooker.kaleido.utils.KaleidoUtils;
import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-11-15 17:37:00
 * @Description TODO
 */
@Data
public class Song {

    private String id;
    private Integer no;
    private String name;

    public String getSimpleName() {
        return KaleidoUtils.getSimpleName(name);
    }
}
