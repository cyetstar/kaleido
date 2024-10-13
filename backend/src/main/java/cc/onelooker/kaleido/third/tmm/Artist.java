package cc.onelooker.kaleido.third.tmm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-11-16 01:11:00
 * @Description TODO
 */
@Data
public class Artist {

    @JsonProperty("netease_id")
    private String neteaseId;

    private String name;

    private String trans;

    @JsonProperty("pic_url")
    private String picUrl;
}
