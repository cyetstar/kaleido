package cc.onelooker.kaleido.dto.resp;

import cc.onelooker.kaleido.third.tmm.Song;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2024-10-12 21:04:00
 * @Description TODO
 */
@Data
public class MusicAlbumViewMatchInfoResp {

    private String title;

    private Integer duration;

    private Integer trackIndex;

    private String artist;

    private String publishTime;

    private String songTitle;

    private Integer songDuration;

    private Integer songTrackIndex;

    private String songArtist;

    private String songPublishTime;

    public String getDurationLabel() {
        return formatDuration(duration);
    }

    public String getSongDurationLabel() {
        return formatDuration(songDuration);
    }

    private String formatDuration(Integer duration) {
        if (duration != null) {
            return String.format("%d:%02d", duration / 1000 / 60, duration % 60);
        }
        return null;
    }


}
