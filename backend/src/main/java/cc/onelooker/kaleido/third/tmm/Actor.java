package cc.onelooker.kaleido.third.tmm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2024-01-04 14:27:00
 * @Description TODO
 */
@Data
public class Actor {
    @JsonProperty("douban_id")
    private String doubanId;
    @JsonProperty("cn_name")
    private String cnName;
    @JsonProperty("en_name")
    private String enName;
    private String role;
    private String thumb;
}
