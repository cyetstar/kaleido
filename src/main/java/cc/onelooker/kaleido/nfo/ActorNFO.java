package cc.onelooker.kaleido.nfo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ActorNFO {

    private String name;
    private String role;
    private String order;
    private String thumb;

}
