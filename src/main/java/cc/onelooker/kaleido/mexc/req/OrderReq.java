package cc.onelooker.kaleido.mexc.req;

import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2023-07-12 17:31:00
 * @Description TODO
 */
@Data
public class OrderReq {

    private String symbol;
    private String side;
    private String type;
    private String quantity;
    private String quoteOrderQty;
    private String price;
    private String newClientOrderId;
    private String recvWindow;
    private String timestamp;
}
