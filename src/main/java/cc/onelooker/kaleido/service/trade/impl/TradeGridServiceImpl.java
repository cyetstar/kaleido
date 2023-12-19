package cc.onelooker.kaleido.service.trade.impl;

import cc.onelooker.kaleido.convert.trade.TradeGridConvert;
import cc.onelooker.kaleido.dto.trade.TradeGridDTO;
import cc.onelooker.kaleido.entity.trade.TradeGridDO;
import cc.onelooker.kaleido.mapper.trade.TradeGridMapper;
import cc.onelooker.kaleido.service.trade.TradeGridService;
import cc.onelooker.kaleido.service.trade.TradeOrderService;
import cc.onelooker.kaleido.service.trade.TradeStrategyService;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 交易网格ServiceImpl
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
@Service
public class TradeGridServiceImpl extends AbstractBaseServiceImpl<TradeGridMapper, TradeGridDO, TradeGridDTO> implements TradeGridService {

    TradeGridConvert convert = TradeGridConvert.INSTANCE;

    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private TradeStrategyService tradeStrategyService;

    @Override
    protected Wrapper<TradeGridDO> genQueryWrapper(TradeGridDTO dto) {
        LambdaQueryWrapper<TradeGridDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getSymbolId()), TradeGridDO::getSymbolId, dto.getSymbolId());
        query.eq(Objects.nonNull(dto.getStrategyId()), TradeGridDO::getStrategyId, dto.getStrategyId());
        query.eq(Objects.nonNull(dto.getRcjg()), TradeGridDO::getRcjg, dto.getRcjg());
        query.eq(Objects.nonNull(dto.getCcjg()), TradeGridDO::getCcjg, dto.getCcjg());
        query.eq(Objects.nonNull(dto.getTlcs()), TradeGridDO::getTlcs, dto.getTlcs());
        query.eq(Objects.nonNull(dto.getTlje()), TradeGridDO::getTlje, dto.getTlje());
        query.eq(StringUtils.isNotEmpty(dto.getWgfx()), TradeGridDO::getWgfx, dto.getWgfx());
        query.eq(StringUtils.isNotEmpty(dto.getSfyx()), TradeGridDO::getSfyx, dto.getSfyx());
        return query;
    }

    @Override
    public TradeGridDTO convertToDTO(TradeGridDO tradeGridDO) {
        return convert.convert(tradeGridDO);
    }

    @Override
    public TradeGridDO convertToDO(TradeGridDTO tradeGridDTO) {
        return convert.convertToDO(tradeGridDTO);
    }

    @Override
    @Transactional
    public TradeGridDTO insert(TradeGridDTO dto) {
        dto.setTlcs(0);
        dto.setTlje("0");
        dto.setSfyx(Constants.NO);
        return super.insert(dto);
    }

    @Override
    public List<TradeGridDTO> listByStrategyId(Long strategyId) {
        TradeGridDTO param = new TradeGridDTO();
        param.setStrategyId(strategyId);
        return list(param);
    }

    @Override
    @Transactional
    public void deleteByStrategyId(Long strategyId) {
        List<TradeGridDTO> tradeGridDTOList = listByStrategyId(strategyId);
        for (TradeGridDTO tradeGridDTO : tradeGridDTOList) {
            tradeOrderService.deleteByGridId(tradeGridDTO.getId());
            deleteById(tradeGridDTO.getId());
        }
    }

    @Override
    @Transactional
    public void createBuyOrder(Long id) {
        TradeGridDTO tradeGridDTO = findById(id);
//        TradeStrategyDTO tradeStrategyDTO = tradeStrategyService.findById(tradeGridDTO.getStrategyId());
        tradeGridDTO.setWgfx(KaleidoConstants.DDFX_BUY);
//        if (StringUtils.equals(tradeStrategyDTO.getJyfx(), KaleidoConstants.DDFX_SELL)) {
//            tradeGridDTO.setTlcs(ObjectUtils.defaultIfNull(tradeGridDTO.getTlcs(), 0) + 1);
//            tradeGridDTO.setTlje()
//        }

    }

    @Override
    @Transactional
    public void createSellOrder(Long id) {

    }
}