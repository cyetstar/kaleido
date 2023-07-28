package cc.onelooker.kaleido.convert.trade;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.trade.TradeStrategyDTO;
import cc.onelooker.kaleido.entity.trade.TradeStrategyDO;
import cc.onelooker.kaleido.dto.trade.req.TradeStrategyPageReq;
import cc.onelooker.kaleido.dto.trade.req.TradeStrategyCreateReq;
import cc.onelooker.kaleido.dto.trade.req.TradeStrategyUpdateReq;
import cc.onelooker.kaleido.dto.trade.resp.TradeStrategyPageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeStrategyViewResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeStrategyCreateResp;
import cc.onelooker.kaleido.exp.trade.TradeStrategyExp;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import com.zjjcnt.common.core.annotation.Dict;
import java.math.BigDecimal;
import java.lang.Integer;
/**
* 策略Convert
*
* @author cyetstar
* @date 2023-07-05 23:02:49
*/
@Mapper
public interface TradeStrategyConvert {

    TradeStrategyConvert INSTANCE = Mappers.getMapper(TradeStrategyConvert.class);

    TradeStrategyDTO convert(TradeStrategyDO entity);

    @InheritInverseConfiguration(name="convert")
    TradeStrategyDO convertToDO(TradeStrategyDTO dto);

    TradeStrategyDTO convertToDTO(TradeStrategyPageReq req);

    TradeStrategyDTO convertToDTO(TradeStrategyCreateReq req);

    TradeStrategyDTO convertToDTO(TradeStrategyUpdateReq req);

    TradeStrategyPageResp convertToPageResp(TradeStrategyDTO dto);

    TradeStrategyViewResp convertToViewResp(TradeStrategyDTO dto);

    TradeStrategyCreateResp convertToCreateResp(TradeStrategyDTO dto);

    TradeStrategyExp convertToExp(TradeStrategyDTO dto);

}