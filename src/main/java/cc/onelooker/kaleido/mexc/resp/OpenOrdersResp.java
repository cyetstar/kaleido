package cc.onelooker.kaleido.mexc.resp;

import lombok.Data;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-07-27 14:25:00
 * @Description TODO
 */
@Data
public class OpenOrdersResp {

    List<OrderResp> orderRespList;

}
