package cc.onelooker.kaleido.third.tmm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-11-15 17:49:00
 * @Description TODO
 */
@Data
public class Album {

    @JsonProperty("netease_id")
    private String neteaseId;

    private String name;

    private String company;

    @JsonProperty("pic_url")
    private String picUrl;

    private String description;

    @JsonProperty("publish_time")
    private Long publishTime;

    private Artist artist;

    private List<Song> songs;
}
