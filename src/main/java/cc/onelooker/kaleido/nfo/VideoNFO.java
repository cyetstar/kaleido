package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class VideoNFO {

    private String codec;
    private String aspect;
    private String width;
    private String height;
    private String durationinseconds;
    private String stereomode;
}
