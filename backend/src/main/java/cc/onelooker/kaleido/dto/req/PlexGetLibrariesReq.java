package cc.onelooker.kaleido.dto.req;

import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-11-12 22:22:00
 * @Description TODO
 */
@Data
public class PlexGetLibrariesReq {

    private String plexUrl;

    private String plexToken;
}
