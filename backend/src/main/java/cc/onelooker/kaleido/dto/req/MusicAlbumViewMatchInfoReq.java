package cc.onelooker.kaleido.dto.req;

import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-11-18 20:44:00
 * @Description TODO
 */
@Data
public class MusicAlbumViewMatchInfoReq {

    private String neteaseId;

    private String musicbrainzId;

    private String path;
}
