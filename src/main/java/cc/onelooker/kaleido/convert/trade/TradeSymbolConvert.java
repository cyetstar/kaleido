package cc.onelooker.kaleido.convert.trade;

import cc.onelooker.kaleido.dto.trade.TradeSymbolDTO;
import cc.onelooker.kaleido.dto.trade.req.TradeSymbolCreateReq;
import cc.onelooker.kaleido.dto.trade.req.TradeSymbolPageReq;
import cc.onelooker.kaleido.dto.trade.req.TradeSymbolUpdateReq;
import cc.onelooker.kaleido.dto.trade.resp.TradeSymbolCreateResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeSymbolPageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeSymbolViewResp;
import cc.onelooker.kaleido.entity.trade.TradeSymbolDO;
import cc.onelooker.kaleido.exp.trade.TradeSymbolExp;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 交易商品Convert
*
* @author cyetstar
* @date 2023-07-06 11:56:08
*/
@Mapper
public interface TradeSymbolConvert {

    TradeSymbolConvert INSTANCE = Mappers.getMapper(TradeSymbolConvert.class);

    TradeSymbolDTO convert(TradeSymbolDO entity);

    @InheritInverseConfiguration(name="convert")
    TradeSymbolDO convertToDO(TradeSymbolDTO dto);

    TradeSymbolDTO convertToDTO(TradeSymbolPageReq req);

    TradeSymbolDTO convertToDTO(TradeSymbolCreateReq req);

    TradeSymbolDTO convertToDTO(TradeSymbolUpdateReq req);

    TradeSymbolPageResp convertToPageResp(TradeSymbolDTO dto);

    TradeSymbolViewResp convertToViewResp(TradeSymbolDTO dto);

    TradeSymbolCreateResp convertToCreateResp(TradeSymbolDTO dto);

    TradeSymbolExp convertToExp(TradeSymbolDTO dto);

}