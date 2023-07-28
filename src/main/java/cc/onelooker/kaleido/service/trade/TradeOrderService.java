package cc.onelooker.kaleido.service.trade;

import cc.onelooker.kaleido.dto.trade.TradeGridDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;

import com.zjjcnt.common.core.annotation.Dict;

import java.lang.Integer;

import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import cc.onelooker.kaleido.dto.trade.TradeOrderDTO;

/**
 * 交易订单Service
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
public interface TradeOrderService extends IBaseService<TradeOrderDTO> {

    PageResult<TradeOrderDTO> pageByGridId(Long gridId, Page<TradeOrderDTO> page);

    void deleteByGridId(Long gridId);

    void order(Long id);

    void updateStatus(TradeOrderDTO tradeOrderDTO);

    void createNewOrder(TradeGridDTO tradeGridDTO, String ddfx);
}