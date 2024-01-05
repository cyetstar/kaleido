package cc.onelooker.kaleido.third.tmm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2024-01-04 13:57:00
 * @Description TODO
 */
@Data
public class Doulist {
    @JsonProperty("douban_id")
    private String doubanId;
    private String title;
    private String about;
    private String thumb;
    private Integer total;
    private String updated;
}
