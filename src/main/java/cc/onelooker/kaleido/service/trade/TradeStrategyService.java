package cc.onelooker.kaleido.service.trade;

import cc.onelooker.kaleido.dto.trade.TradeStrategyDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 策略Service
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
public interface TradeStrategyService extends IBaseService<TradeStrategyDTO> {

    void start(Long id);

    void stop(Long id);
}