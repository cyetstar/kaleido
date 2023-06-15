package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class UniqueidNFO {

    @XmlAttribute
    private String type;
    @XmlValue
    private String value;
    @XmlAttribute(name = "default")
    private Boolean def;
}
