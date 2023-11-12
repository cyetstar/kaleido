package cc.onelooker.kaleido.service.trade.impl;

import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cc.onelooker.kaleido.service.trade.TradeRuleService;
import cc.onelooker.kaleido.entity.trade.TradeRuleDO;
import cc.onelooker.kaleido.dto.trade.TradeRuleDTO;
import cc.onelooker.kaleido.convert.trade.TradeRuleConvert;
import cc.onelooker.kaleido.mapper.trade.TradeRuleMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * 交易规则ServiceImpl
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */
@Service
public class TradeRuleServiceImpl extends KaleidoBaseServiceImpl<TradeRuleMapper, TradeRuleDO, TradeRuleDTO> implements TradeRuleService {

    TradeRuleConvert convert = TradeRuleConvert.INSTANCE;

    @Override
    protected Wrapper<TradeRuleDO> genQueryWrapper(TradeRuleDTO dto) {
        LambdaQueryWrapper<TradeRuleDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getAccountId()), TradeRuleDO::getAccountId, dto.getAccountId());
        query.eq(StringUtils.isNotEmpty(dto.getYkb()), TradeRuleDO::getYkb, dto.getYkb());
        query.eq(StringUtils.isNotEmpty(dto.getFxb()), TradeRuleDO::getFxb, dto.getFxb());
        query.eq(StringUtils.isNotEmpty(dto.getFxje()), TradeRuleDO::getFxje, dto.getFxje());
        return query;
    }

    @Override
    public TradeRuleDTO convertToDTO(TradeRuleDO tradeRuleDO) {
        return convert.convert(tradeRuleDO);
    }

    @Override
    public TradeRuleDO convertToDO(TradeRuleDTO tradeRuleDTO) {
        return convert.convertToDO(tradeRuleDTO);
    }
}