package cc.onelooker.kaleido.convert.trade;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.trade.TradeGridStrategyDTO;
import cc.onelooker.kaleido.entity.trade.TradeGridStrategyDO;
import cc.onelooker.kaleido.dto.trade.req.TradeGridStrategyPageReq;
import cc.onelooker.kaleido.dto.trade.req.TradeGridStrategyCreateReq;
import cc.onelooker.kaleido.dto.trade.req.TradeGridStrategyUpdateReq;
import cc.onelooker.kaleido.dto.trade.resp.TradeGridStrategyPageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeGridStrategyViewResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeGridStrategyCreateResp;
import cc.onelooker.kaleido.exp.trade.TradeGridStrategyExp;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import com.zjjcnt.common.core.annotation.Dict;
import java.math.BigDecimal;
import java.lang.Integer;
/**
* 网格策略Convert
*
* @author cyetstar
* @date 2023-07-05 23:02:49
*/
@Mapper
public interface TradeGridStrategyConvert {

    TradeGridStrategyConvert INSTANCE = Mappers.getMapper(TradeGridStrategyConvert.class);

    TradeGridStrategyDTO convert(TradeGridStrategyDO entity);

    @InheritInverseConfiguration(name="convert")
    TradeGridStrategyDO convertToDO(TradeGridStrategyDTO dto);

    TradeGridStrategyDTO convertToDTO(TradeGridStrategyPageReq req);

    TradeGridStrategyDTO convertToDTO(TradeGridStrategyCreateReq req);

    TradeGridStrategyDTO convertToDTO(TradeGridStrategyUpdateReq req);

    TradeGridStrategyPageResp convertToPageResp(TradeGridStrategyDTO dto);

    TradeGridStrategyViewResp convertToViewResp(TradeGridStrategyDTO dto);

    TradeGridStrategyCreateResp convertToCreateResp(TradeGridStrategyDTO dto);

    TradeGridStrategyExp convertToExp(TradeGridStrategyDTO dto);

}