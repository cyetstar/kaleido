package cc.onelooker.kaleido.dto.music.resp;

import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2023-11-16 00:57:00
 * @Description TODO
 */
@Data
public class MusicArtistSearchNeteaseResp {

    private String neteaseId;

    private String name;

    private String trans;

    private String picUrl;
}
