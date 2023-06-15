package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ThumbNFO {

    @XmlAttribute
    private String aspect = Aspect.fanart.name();
    @XmlAttribute
    private String preview;
    @XmlValue
    private String value;
}
