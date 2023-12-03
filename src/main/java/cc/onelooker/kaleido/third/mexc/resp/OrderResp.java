package cc.onelooker.kaleido.third.mexc.resp;

import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2023-07-13 15:01:00
 * @Description TODO
 */
@Data
public class OrderResp {

    private String symbol;    //交易对
    private String origClientOrderId;    //原始客户端订单id
    private String orderId;    //系统订单id
    private String clientOrderId;    //客户自定义id
    private String price;    //价格
    private String origOty;    //原始订单数量
    private String executedQty;    //交易的订单数量
    private String cummulativeQuoteQty;    //累计订单金额
    private String status;    //订单状态
    private String timeInForce;    //订单的时效方式
    private String type;    //订单类型
    private String side;    //订单方向
    private String stopPrice;    //止损价格
    private String icebergQty;    //冰山数量
    private String time;    //订单时间
    private String updateTime;    //最后更新时间
    private String isWorking;    //是否在orderbook中
    private String origQuoteOrderQty;    //原始的交易金额
}
