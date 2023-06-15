package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class RatingNFO {

    private Float value;
    private Integer votes;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private Integer max;
    @XmlAttribute(name = "default")
    private Boolean def;
}
