package cc.onelooker.kaleido.convert.trade;

import cc.onelooker.kaleido.dto.trade.TradeLogDTO;
import cc.onelooker.kaleido.dto.trade.req.TradeLogCreateReq;
import cc.onelooker.kaleido.dto.trade.req.TradeLogPageReq;
import cc.onelooker.kaleido.dto.trade.req.TradeLogUpdateReq;
import cc.onelooker.kaleido.dto.trade.resp.TradeLogCreateResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeLogPageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeLogViewResp;
import cc.onelooker.kaleido.entity.trade.TradeLogDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 交易记录Convert
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */
@Mapper
public interface TradeLogConvert {

    TradeLogConvert INSTANCE = Mappers.getMapper(TradeLogConvert.class);

    TradeLogDTO convert(TradeLogDO entity);

    @InheritInverseConfiguration(name = "convert")
    TradeLogDO convertToDO(TradeLogDTO dto);

    TradeLogDTO convertToDTO(TradeLogPageReq req);

    TradeLogDTO convertToDTO(TradeLogCreateReq req);

    TradeLogDTO convertToDTO(TradeLogUpdateReq req);

    TradeLogPageResp convertToPageResp(TradeLogDTO dto);

    TradeLogViewResp convertToViewResp(TradeLogDTO dto);

    TradeLogCreateResp convertToCreateResp(TradeLogDTO dto);

}