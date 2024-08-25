package cc.onelooker.kaleido.dto.resp;

import cc.onelooker.kaleido.third.netease.Song;
import lombok.Data;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2024-02-01 16:30:00
 * @Description TODO
 */
@Data
public class MusicAlbumViewNeteaseResp {

    private String neteaseId;

    private String title;

    private String artist;

    private List<Song> songs;
}
