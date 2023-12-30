package cc.onelooker.kaleido.third.douban;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-12-21 22:22:00
 * @Description TODO
 */
@Data
public class Subject {

    @JSONField(name = "subject")
    private Movie movie;

    private Integer rank;

    private Integer delta;
}
