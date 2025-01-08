package cc.onelooker.kaleido.third.plex;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.collections4.IterableUtils;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-12-02 21:00:00
 * @Description TODO
 */
@Data
public class Media {

    private String id;
    private Integer duration;
    private Integer width;
    private Integer height;
    private Float aspectRatio;
    private Integer bitrate;
    private Integer audioChannels;
    private String audioCodec;
    private String videoCodec;
    private String videoResolution;
    private String container;
    private String videoFrameRate;
    private String videoProfile;
    @JsonProperty("Part")
    private List<Part> partList;

    @JsonIgnore
    public Part getPart() {
        return IterableUtils.get(partList, 0);
    }

    @Data
    public static class Part {
        private String id;
        private String key;
        private Integer duration;
        private String file;
        private Long size;
        private String container;
        private String videoProfile;
        private String hasThumbnail;
        @JsonProperty("Stream")
        private List<Stream> streamList;

        @JsonIgnore
        public Stream getStream() {
            return IterableUtils.get(streamList, 0);
        }
    }

    @Data
    public static class Stream {
        private String id;
        private Integer streamType;
        @JsonProperty("default")
        private Boolean isDefault;
        private Boolean selected;
        private String codec;
        private Integer index;
        private Integer bitrate;
        private String language;
        private String languageTag;
        private String languageCode;
        private Integer bitDepth;
        private String chromaLocation;
        private String chromaSubsampling;
        private Integer codedHeight;
        private Integer codedWidth;
        private String colorRange;
        private Float frameRate;
        private Integer height;
        private Integer width;
        private Integer level;
        private String profile;
        private Integer refFrames;
        private String title;
        private String displayTitle;
        private String extendedDisplayTitle;
    }

}
