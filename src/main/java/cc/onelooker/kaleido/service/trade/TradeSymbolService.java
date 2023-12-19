package cc.onelooker.kaleido.service.trade;

import cc.onelooker.kaleido.dto.trade.TradeSymbolDTO;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

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