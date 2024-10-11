package cc.onelooker.kaleido.dto.req;

import lombok.Data;

import java.util.Map;

/**
 * @Author cyetstar
 * @Date 2023-12-26 11:33:00
 * @Description TODO
 */
@Data
public class ActionStartReq {

    private String action;

    private Map<String, String> params;

}
