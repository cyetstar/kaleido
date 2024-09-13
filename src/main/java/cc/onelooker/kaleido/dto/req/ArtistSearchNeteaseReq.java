package cc.onelooker.kaleido.dto.req;

import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-11-16 00:53:00
 * @Description TODO
 */
@Data
public class ArtistSearchNeteaseReq {

    private String keywords;

    private Integer limit = 30;
}

