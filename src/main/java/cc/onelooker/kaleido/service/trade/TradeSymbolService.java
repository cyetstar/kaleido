package cc.onelooker.kaleido.service.trade;

import com.zjjcnt.common.core.service.IBaseService;

import java.lang.String;
import java.lang.Long;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import java.math.BigDecimal;
import java.util.List;

import cc.onelooker.kaleido.dto.trade.TradeSymbolDTO;

/**
 * 交易商品Service
 *
 * @author cyetstar
 * @date 2023-07-06 11:56:08
 */
public interface TradeSymbolService extends IBaseService<TradeSymbolDTO> {

    void batchSave(List<String> spmcList, String jys);

    void like(Long id, String sfsc);
}