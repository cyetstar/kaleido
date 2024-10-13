package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author xiadawei
 * @Date 2024-10-12 16:28:00
 * @Description TODO
 */
@Data
@XmlRootElement(name = "album")
@XmlAccessorType(XmlAccessType.FIELD)
public class AlbumNFO {

    @XmlElement(name = "neteaseid")
    private String neteaseId;

    @XmlElement(name = "musicbrainzid")
    private String musicbrainzId;
}
