package cc.onelooker.kaleido.schedule;

import cc.onelooker.kaleido.dto.trade.TradeGridDTO;
import cc.onelooker.kaleido.dto.trade.TradeOrderDTO;
import cc.onelooker.kaleido.dto.trade.TradeStrategyDTO;
import cc.onelooker.kaleido.dto.trade.TradeSymbolDTO;
import cc.onelooker.kaleido.mexc.MexcApiService;
import cc.onelooker.kaleido.mexc.req.OrderReq;
import cc.onelooker.kaleido.mexc.resp.OrderResp;
import cc.onelooker.kaleido.service.trade.TradeGridService;
import cc.onelooker.kaleido.service.trade.TradeOrderService;
import cc.onelooker.kaleido.service.trade.TradeStrategyService;
import cc.onelooker.kaleido.service.trade.TradeSymbolService;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.util.DateTimeUtils;
import com.zjjcnt.common.util.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author xiadawei
 * @Date 2023-07-12 17:20:00
 * @Description TODO
 */
@Slf4j
@Component
public class OrderJob {

    @Autowired
    private MexcApiService mexcApiService;

    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private TradeSymbolService tradeSymbolService;

    @Autowired
    private TradeStrategyService tradeStrategyService;

    @Autowired
    private TradeGridService tradeGridService;

    private Map<Long, String> symbolMap;

    @PostConstruct
    public void init() {
        List<TradeSymbolDTO> tradeSymbolDTOList = tradeSymbolService.list(null);
        symbolMap = tradeSymbolDTOList.stream().collect(Collectors.toMap(TradeSymbolDTO::getId, TradeSymbolDTO::getSpmc));
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void order() {
        while (true) {
            TradeOrderDTO param = new TradeOrderDTO();
            param.setFsbz(KaleidoConstants.FSBZ_DFS);
            PageResult<TradeOrderDTO> pageResult = tradeOrderService.page(param, Page.of(1, 20));
            List<TradeOrderDTO> records = pageResult.getRecords();
            if (CollectionUtils.isEmpty(records)) {
                break;
            }
            for (TradeOrderDTO record : records) {
                try {
                    OrderReq req = convertToOrderReq(record);
                    OrderResp resp = mexcApiService.order(req);
                    record.setJysddh(resp.getOrderId());
                    record.setFsbz(KaleidoConstants.FSBZ_YFS);
                } catch (Exception e) {
                    record.setFsbz(KaleidoConstants.FSBZ_FSSB);
                }
                record.setFssj(DateTimeUtils.now());
                tradeOrderService.update(record);
            }
        }
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void checkStrategy() {
        List<TradeStrategyDTO> tradeStrategyDTOList = listRunningTradeStrategy();
        for (TradeStrategyDTO tradeStrategyDTO : tradeStrategyDTOList) {
            checkOrder(tradeStrategyDTO);
        }
    }

    private void checkOrder(TradeStrategyDTO tradeStrategyDTO) {
        List<OrderResp> orderRespList = mexcApiService.allOrders(MapUtils.getString(symbolMap, tradeStrategyDTO.getSymbolId()));
        Map<String, OrderResp> filledOrderRespMap = orderRespList.stream().filter(s -> !StringUtils.equals(s.getStatus(), KaleidoConstants.DDZT_NEW)).collect(Collectors.toMap(OrderResp::getOrigClientOrderId, Function.identity()));
        List<TradeOrderDTO> tradeOrderDTOList = listNewOrder(tradeStrategyDTO.getId());
        for (TradeOrderDTO tradeOrderDTO : tradeOrderDTOList) {
            OrderResp orderResp = filledOrderRespMap.get(String.valueOf(tradeOrderDTO.getId()));
            if (orderResp != null) {
                // 已成交更新订单信息
                tradeOrderDTO.setJysl(orderResp.getExecutedQty());
                tradeOrderDTO.setLjddje(orderResp.getCummulativeQuoteQty());
                tradeOrderDTO.setDdzt(orderResp.getStatus());
                tradeOrderDTO.setGxsj(formatDateTime(orderResp.getUpdateTime()));
                tradeOrderService.updateStatus(tradeOrderDTO);
                //

                tradeGridService.createBuyOrder(tradeOrderDTO.getGridId());

            }
        }
    }

    private List<TradeOrderDTO> listNewOrder(Long strategyId) {
        TradeOrderDTO param = new TradeOrderDTO();
        param.setDdzt(KaleidoConstants.DDZT_NEW);
        param.setStrategyId(strategyId);
        return tradeOrderService.list(param);
    }

    private List<TradeStrategyDTO> listRunningTradeStrategy() {
        TradeStrategyDTO param = new TradeStrategyDTO();
        param.setSfyx(Constants.YES);
        return tradeStrategyService.list(param);
    }

    private OrderReq convertToOrderReq(TradeOrderDTO tradeOrderDTO) {
        OrderReq orderReq = new OrderReq();
        orderReq.setNewClientOrderId(String.valueOf(tradeOrderDTO.getId()));
        orderReq.setType(tradeOrderDTO.getDdlx());
        orderReq.setSide(tradeOrderDTO.getDdfx());
        orderReq.setQuantity(tradeOrderDTO.getYssl());
        orderReq.setPrice(tradeOrderDTO.getJg());
        orderReq.setQuoteOrderQty(tradeOrderDTO.getYsjyje());
        orderReq.setSymbol(MapUtils.getString(symbolMap, tradeOrderDTO.getSymbolId()));
        return orderReq;
    }

    private String formatDateTime(String currentTimeMillis) {
        Date date = new Date(Long.valueOf(currentTimeMillis));
        return DateFormatUtils.format(date, DateTimeUtils.DATETIME_PATTERN);
    }
}
