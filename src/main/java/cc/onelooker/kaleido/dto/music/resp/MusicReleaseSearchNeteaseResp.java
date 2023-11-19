package cc.onelooker.kaleido.dto.music.resp;

import com.zjjcnt.common.core.annotation.StringDateFormat;
import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2023-11-16 00:57:00
 * @Description TODO
 */
@Data
public class MusicReleaseSearchNeteaseResp {

    private String bt;

    private String neteaseId;

    private String ysj;

    @StringDateFormat
    private String fxrq;

    private String picUrl;
}
