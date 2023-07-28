package cc.onelooker.kaleido.service.trade;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import com.zjjcnt.common.core.annotation.Dict;
import java.math.BigDecimal;
import java.lang.Integer;
import cc.onelooker.kaleido.dto.trade.TradeStrategyDTO;

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