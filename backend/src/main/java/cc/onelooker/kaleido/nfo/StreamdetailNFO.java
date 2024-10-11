package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class StreamdetailNFO {

    private VideoNFO video;
    private List<AudioNFO> audio;
    private List<SubtitleNFO> subtitle;

}
