package cc.onelooker.kaleido.convert.trade;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.trade.TradeOrderDTO;
import cc.onelooker.kaleido.entity.trade.TradeOrderDO;
import cc.onelooker.kaleido.dto.trade.req.TradeOrderPageReq;
import cc.onelooker.kaleido.dto.trade.req.TradeOrderCreateReq;
import cc.onelooker.kaleido.dto.trade.req.TradeOrderUpdateReq;
import cc.onelooker.kaleido.dto.trade.resp.TradeOrderPageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeOrderViewResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeOrderCreateResp;
import cc.onelooker.kaleido.exp.trade.TradeOrderExp;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
/**
* 交易订单Convert
*
* @author cyetstar
* @date 2023-07-05 23:02:49
*/
@Mapper
public interface TradeOrderConvert {

    TradeOrderConvert INSTANCE = Mappers.getMapper(TradeOrderConvert.class);

    TradeOrderDTO convert(TradeOrderDO entity);

    @InheritInverseConfiguration(name="convert")
    TradeOrderDO convertToDO(TradeOrderDTO dto);

    TradeOrderDTO convertToDTO(TradeOrderPageReq req);

    TradeOrderDTO convertToDTO(TradeOrderCreateReq req);

    TradeOrderDTO convertToDTO(TradeOrderUpdateReq req);

    TradeOrderPageResp convertToPageResp(TradeOrderDTO dto);

    TradeOrderViewResp convertToViewResp(TradeOrderDTO dto);

    TradeOrderCreateResp convertToCreateResp(TradeOrderDTO dto);

    TradeOrderExp convertToExp(TradeOrderDTO dto);

}