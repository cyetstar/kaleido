package cc.onelooker.kaleido.dto.resp;

import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-11-16 00:57:00
 * @Description TODO
 */
@Data
public class MusicAlbumSearchInfoResp {

    private String existId;

    private String neteaseId;

    private String musicbrainzId;

    private String title;

    private String artistName;

    private String publishTime;

    private String picUrl;
}
