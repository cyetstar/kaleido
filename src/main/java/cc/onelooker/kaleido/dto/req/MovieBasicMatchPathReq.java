package cc.onelooker.kaleido.dto.req;

import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-12-03 19:50:00
 * @Description TODO
 */
@Data
public class MovieBasicMatchPathReq {

    private String path;

    private String doubanId;

    private String tmdbId;

}
