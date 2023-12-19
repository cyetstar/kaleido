package cc.onelooker.kaleido.service.trade;

import cc.onelooker.kaleido.dto.trade.TradeGridDTO;
import cc.onelooker.kaleido.dto.trade.TradeOrderDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;

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