package cc.onelooker.kaleido.mexc.resp;

import lombok.Data;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-07-17 14:04:00
 * @Description TODO
 */
@Data
public class DefaultSymbolsResp {

    private Integer code;
    private String msg;
    private Long timestamp;
    private List<String> data;
}
