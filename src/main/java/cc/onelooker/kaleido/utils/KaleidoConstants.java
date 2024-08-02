package cc.onelooker.kaleido.utils;

/**
 * @Author cyetstar
 * @Date 2022-06-13 09:54:00
 * @Description TODO
 */
public class KaleidoConstants {

    public static final String COMIC_INFO = "ComicInfo.xml";
    public static final String COMIC_COVER = "cover.jpg";

    public static final String SFJG_WIN = "1";
    public static final String SFJG_LOSS = "0";

    /**
     * 发生标志
     */
    public static final String FSBZ_DFS = "0";
    public static final String FSBZ_YFS = "1";
    public static final String FSBZ_FSSB = "2";

    /**
     * 交易所
     */
    public static final String JYS_MEXC = "MEXC";

    public static final String WGMS_DB = "1";
    public static final String WGMS_DC = "2";

    /**
     * 订单方向
     */
    public static final String DDFX_BUY = "BUY"; //买入
    public static final String DDFX_SELL = "SELL";  //卖出

    /**
     * 订单方向
     */
    public static final String DDLX_LIMIT = "LIMIT"; //限价单
    public static final String DDLX_MARKET = "MARKET"; //市价单
    public static final String DDLX_LIMIT_MAKER = "LIMIT_MAKER"; //限价只挂单
    public static final String DDLX_IMMEDIATE_OR_CANCEL = "IMMEDIATE_OR_CANCEL"; //IOC单(无法立即成交的部分就撤销, 订单在失效前会尽量多的成交。)
    public static final String DDLX_FILL_OR_KILL = "FILL_OR_KILL"; //FOK单(无法全部立即成交就撤销, 如果无法全部成交, 订单会失效。)

    /**
     * 订单状态
     */
    public static final String DDZT_NEW = "NEW"; // 未成交
    public static final String DDZT_FILLED = "FILLED"; // 已成交
    public static final String DDZT_PARTIALLY_FILLED = "PARTIALLY_FILLED"; // 部分成交
    public static final String DDZT_CANCELED = "CANCELED"; // 已撤销
    public static final String DDZT_PARTIALLY_CANCELED = "PARTIALLY_CANCELED"; // 部分撤销
    public static final String DDZT_WITHDRAW = "WITHDRAW";// 提现
    public static final String DDZT_WITHDRAW_FEE = "WITHDRAW_FEE"; // 提现手续费
    public static final String DDZT_DEPOSIT = "DEPOSIT";// 充值
    public static final String DDZT_DEPOSIT_FEE = "DEPOSIT_FEE";// 充值手续费
    public static final String DDZT_ENTRUST = "ENTRUST";// 委托成交
    public static final String DDZT_ENTRUST_PLACE = "ENTRUST_PLACE";// 下单
    public static final String DDZT_ENTRUST_CANCEL = "ENTRUST_CANCEL";// 撤单
    public static final String DDZT_TRADE_FEE = "TRADE_FEE";// 手续费
    public static final String DDZT_ENTRUST_UNFROZEN = "ENTRUST_UNFROZEN";// 订单冻结资金返还
    public static final String DDZT_SUGAR = "SUGAR";// 空投
    public static final String DDZT_ETF_INDEX = "ETF_INDEX";// ETF下单

    public static final String BSLX_IMDB = "imdb";
    public static final String BSLX_DOUBAN = "douban";
    public static final String BSLX_TMDB = "tmdb";

    public static final String TASK_STATUS_TODO = "0";
    public static final String TASK_STATUS_DONE = "1";
    public static final String TASK_STATUS_ERROR = "4";
}
