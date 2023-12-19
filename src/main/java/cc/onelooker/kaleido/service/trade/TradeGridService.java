package cc.onelooker.kaleido.service.trade;

import cc.onelooker.kaleido.dto.trade.TradeGridDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

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