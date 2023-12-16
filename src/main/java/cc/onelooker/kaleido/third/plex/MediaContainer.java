package cc.onelooker.kaleido.third.plex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-12-15 16:30:00
 * @Description TODO
 */
@Data
public class MediaContainer {

    private Integer size;
    private String title1;
    private String allowSync;
    private String identifier;
    private String librarySectionID;
    private String librarySectionTitle;
    @JsonProperty("Metadata")
    private List<Metadata> metadataList;
    @JsonProperty("Directory")
    private List<Directory> directoryList;

    public Metadata getMetadata() {
        return CollectionUtils.get(metadataList, 0);
    }
}
