package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class RatingNFO {

    private String value;
    private Integer votes;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private Integer max;
    @XmlAttribute(name = "default")
    private Boolean def;
}
