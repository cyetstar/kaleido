package cc.onelooker.kaleido.dto.resp;

import com.zjjcnt.common.core.annotation.StringDateFormat;
import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-11-16 00:57:00
 * @Description TODO
 */
@Data
public class MusicAlbumSearchNeteaseResp {

    private String neteaseId;

    private String title;

    private String artist;

    @StringDateFormat
    private String publishTime;

    private String picUrl;
}
