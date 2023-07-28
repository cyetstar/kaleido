package cc.onelooker.kaleido.service.trade;

import com.zjjcnt.common.core.service.IBaseService;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Integer;
import java.util.List;

import com.zjjcnt.common.core.annotation.Dict;
import cc.onelooker.kaleido.dto.trade.TradeGridDTO;

/**
 * 交易网格Service
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
public interface TradeGridService extends IBaseService<TradeGridDTO> {

    List<TradeGridDTO> listByStrategyId(Long strategyId);

    void deleteByStrategyId(Long id);

    void createBuyOrder(Long id);

    void createSellOrder(Long id);
}