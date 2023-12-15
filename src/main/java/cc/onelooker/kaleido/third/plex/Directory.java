package cc.onelooker.kaleido.third.plex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-12-15 10:06:00
 * @Description TODO
 */
@Data
public class Directory {
    private String allowSync;
    private String art;
    private String composite;
    private String filters;
    private String refreshing;
    private String thumb;
    private String key;
    private String type;
    private String title;
    private String agent;
    private String scanner;
    private String language;
    private String uuid;
    private Long updatedAt;
    private Long createdAt;
    private Long scannedAt;
    private Long contentChangedAt;
    @JsonProperty("Location")
    private List<Location> locationList;

    public Location getLocation() {
        return CollectionUtils.get(locationList, 0);
    }

    @Data
    public static class Location {
        private Integer id;
        private String path;
    }
}
