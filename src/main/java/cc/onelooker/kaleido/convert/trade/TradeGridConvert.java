package cc.onelooker.kaleido.convert.trade;

import cc.onelooker.kaleido.dto.trade.TradeGridDTO;
import cc.onelooker.kaleido.dto.trade.req.TradeGridCreateReq;
import cc.onelooker.kaleido.dto.trade.req.TradeGridPageReq;
import cc.onelooker.kaleido.dto.trade.req.TradeGridUpdateReq;
import cc.onelooker.kaleido.dto.trade.resp.TradeGridCreateResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeGridListByStrategyIdResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeGridPageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeGridViewResp;
import cc.onelooker.kaleido.entity.trade.TradeGridDO;
import cc.onelooker.kaleido.exp.trade.TradeGridExp;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
* 交易网格Convert
*
* @author cyetstar
* @date 2023-07-05 23:02:49
*/
@Mapper
public interface TradeGridConvert {

    TradeGridConvert INSTANCE = Mappers.getMapper(TradeGridConvert.class);

    TradeGridDTO convert(TradeGridDO entity);

    @InheritInverseConfiguration(name="convert")
    TradeGridDO convertToDO(TradeGridDTO dto);

    TradeGridDTO convertToDTO(TradeGridPageReq req);

    TradeGridDTO convertToDTO(TradeGridCreateReq req);

    TradeGridDTO convertToDTO(TradeGridUpdateReq req);

    TradeGridPageResp convertToPageResp(TradeGridDTO dto);

    TradeGridViewResp convertToViewResp(TradeGridDTO dto);

    TradeGridCreateResp convertToCreateResp(TradeGridDTO dto);

    TradeGridExp convertToExp(TradeGridDTO dto);

    List<TradeGridListByStrategyIdResp> convertToListByStrategyIdResp(List<TradeGridDTO> dtoList);
}