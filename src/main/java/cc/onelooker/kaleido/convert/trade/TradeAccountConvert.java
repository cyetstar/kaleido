package cc.onelooker.kaleido.convert.trade;

import cc.onelooker.kaleido.dto.trade.TradeAccountDTO;
import cc.onelooker.kaleido.dto.trade.req.TradeAccountCreateReq;
import cc.onelooker.kaleido.dto.trade.req.TradeAccountPageReq;
import cc.onelooker.kaleido.dto.trade.req.TradeAccountSaveReq;
import cc.onelooker.kaleido.dto.trade.req.TradeAccountUpdateReq;
import cc.onelooker.kaleido.dto.trade.resp.TradeAccountCreateResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeAccountPageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeAccountViewResp;
import cc.onelooker.kaleido.entity.trade.TradeAccountDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 交易账户Convert
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */
@Mapper
public interface TradeAccountConvert {

    TradeAccountConvert INSTANCE = Mappers.getMapper(TradeAccountConvert.class);

    TradeAccountDTO convert(TradeAccountDO entity);

    @InheritInverseConfiguration(name = "convert")
    TradeAccountDO convertToDO(TradeAccountDTO dto);

    TradeAccountDTO convertToDTO(TradeAccountPageReq req);

    TradeAccountDTO convertToDTO(TradeAccountCreateReq req);

    TradeAccountDTO convertToDTO(TradeAccountUpdateReq req);

    TradeAccountPageResp convertToPageResp(TradeAccountDTO dto);

    TradeAccountViewResp convertToViewResp(TradeAccountDTO dto);

    TradeAccountCreateResp convertToCreateResp(TradeAccountDTO dto);

    TradeAccountDTO convertToDTO(TradeAccountSaveReq req);
}

