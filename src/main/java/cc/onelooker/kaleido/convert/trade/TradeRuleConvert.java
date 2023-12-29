package cc.onelooker.kaleido.convert.trade;

import cc.onelooker.kaleido.dto.trade.TradeRuleDTO;
import cc.onelooker.kaleido.dto.trade.req.TradeRuleCreateReq;
import cc.onelooker.kaleido.dto.trade.req.TradeRulePageReq;
import cc.onelooker.kaleido.dto.trade.req.TradeRuleSaveReq;
import cc.onelooker.kaleido.dto.trade.req.TradeRuleUpdateReq;
import cc.onelooker.kaleido.dto.trade.resp.TradeRuleCreateResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeRulePageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeRuleViewResp;
import cc.onelooker.kaleido.entity.trade.TradeRuleDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 交易规则Convert
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */
@Mapper
public interface TradeRuleConvert {

    TradeRuleConvert INSTANCE = Mappers.getMapper(TradeRuleConvert.class);

    TradeRuleDTO convert(TradeRuleDO entity);

    @InheritInverseConfiguration(name = "convert")
    TradeRuleDO convertToDO(TradeRuleDTO dto);

    TradeRuleDTO convertToDTO(TradeRulePageReq req);

    TradeRuleDTO convertToDTO(TradeRuleCreateReq req);

    TradeRuleDTO convertToDTO(TradeRuleUpdateReq req);

    TradeRulePageResp convertToPageResp(TradeRuleDTO dto);

    TradeRuleViewResp convertToViewResp(TradeRuleDTO dto);

    TradeRuleCreateResp convertToCreateResp(TradeRuleDTO dto);

    TradeRuleDTO convertToDTO(TradeRuleSaveReq req);

}