package cc.onelooker.kaleido.mexc.resp;

import lombok.Data;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-07-06 13:45:00
 * @Description TODO
 */
@Data
public class ExchangeInfoResp {

    private String timezone;

    private Long serverTime;

    private List<String> rateLimits;

    private List<String> exchangeFilters;

    private List<Symbol> symbols;

    @Data
    public static class Symbol {

        private String symbol;
        /**
         * 状态
         */
        private String status;

        /**
         * 交易币
         */
        private String baseAsset;

        /**
         * 交易币精度
         */
        private Integer baseAssetPrecision;

        /**
         * 计价币
         */
        private String quoteAsset;

        /**
         * 计价币价格精度
         */
        private Integer quotePrecision;

        /**
         * 计价币资产精度
         */
        private Integer quoteAssetPrecision;

        /**
         * 交易币手续费精度
         */
        private Integer baseCommissionPrecision;
        /**
         * 计价币手续费精度
         */
        private Integer quoteCommissionPrecision;
        /**
         * 订单类型
         */
        private List<String> orderTypes;

        /**
         * 是否允许市价委托
         */
        private Boolean quoteOrderQtyMarketAllowed;

        /**
         * 是否允许api现货交易
         */
        private Boolean isSpotTradingAllowed;

        /**
         * 是否允许api杠杆交易
         */
        private Boolean isMarginTradingAllowed;

        /**
         * 权限
         */
        private List<String> permissions;

        /**
         * 最大委托数量
         */
        private String maxQuoteAmount;

        /**
         * marker手续费
         */
        private String makerCommission;

        /**
         * taker手续费
         */
        private String takerCommission;

        /**
         * 最小下单金额
         */
        private String quoteAmountPrecision;

        /**
         * 最小下单数量
         */
        private String baseSizePrecision;
    }
}
