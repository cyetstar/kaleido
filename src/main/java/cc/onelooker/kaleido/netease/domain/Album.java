package cc.onelooker.kaleido.netease.domain;

import lombok.Data;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-11-15 17:49:00
 * @Description TODO
 */
@Data
public class Album {

    private String id;

    private String name;

    private String company;

    private String picUrl;

    private Long publishTime;

    private Artist artist;

    private List<Song> songs;
}
