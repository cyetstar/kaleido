package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ActorNFO {

    private String name;
    @XmlElement(name = "originalname")
    private String originalName;
    private String role;
    private String order;
    private String thumb;
    @XmlElement(name = "doubanid")
    private String doubanId;

}
