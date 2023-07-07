package cc.onelooker.kaleido.service.trade.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.trade.TradeGridStrategyService;
import cc.onelooker.kaleido.entity.trade.TradeGridStrategyDO;
import cc.onelooker.kaleido.dto.trade.TradeGridStrategyDTO;
import cc.onelooker.kaleido.convert.trade.TradeGridStrategyConvert;
import cc.onelooker.kaleido.mapper.trade.TradeGridStrategyMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import com.zjjcnt.common.core.annotation.Dict;
import java.math.BigDecimal;
import java.lang.Integer;

/**
 * 网格策略ServiceImpl
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
@Service
public class TradeGridStrategyServiceImpl extends AbstractBaseServiceImpl<TradeGridStrategyMapper, TradeGridStrategyDO, TradeGridStrategyDTO> implements TradeGridStrategyService {

    TradeGridStrategyConvert convert = TradeGridStrategyConvert.INSTANCE;

    @Override
    protected Wrapper<TradeGridStrategyDO> genQueryWrapper(TradeGridStrategyDTO dto) {
        LambdaQueryWrapper<TradeGridStrategyDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getSpdm()), TradeGridStrategyDO::getSpdm, dto.getSpdm());
        query.eq(StringUtils.isNotEmpty(dto.getCjsj()), TradeGridStrategyDO::getCjsj, dto.getCjsj());
        query.eq(StringUtils.isNotEmpty(dto.getXgsj()), TradeGridStrategyDO::getXgsj, dto.getXgsj());
        query.eq(StringUtils.isNotEmpty(dto.getJyfx()), TradeGridStrategyDO::getJyfx, dto.getJyfx());
        query.eq(Objects.nonNull(dto.getQjzgz()), TradeGridStrategyDO::getQjzgz, dto.getQjzgz());
        query.eq(Objects.nonNull(dto.getQjzdz()), TradeGridStrategyDO::getQjzdz, dto.getQjzdz());
        query.eq(StringUtils.isNotEmpty(dto.getWgms()), TradeGridStrategyDO::getWgms, dto.getWgms());
        query.eq(Objects.nonNull(dto.getWgsl()), TradeGridStrategyDO::getWgsl, dto.getWgsl());
        query.eq(Objects.nonNull(dto.getTrje()), TradeGridStrategyDO::getTrje, dto.getTrje());
        query.eq(Objects.nonNull(dto.getQjsysx()), TradeGridStrategyDO::getQjsysx, dto.getQjsysx());
        query.eq(Objects.nonNull(dto.getQjxyxx()), TradeGridStrategyDO::getQjxyxx, dto.getQjxyxx());
        query.eq(StringUtils.isNotEmpty(dto.getSfqjsy()), TradeGridStrategyDO::getSfqjsy, dto.getSfqjsy());
        query.eq(StringUtils.isNotEmpty(dto.getSfqjxy()), TradeGridStrategyDO::getSfqjxy, dto.getSfqjxy());
        query.eq(StringUtils.isNotEmpty(dto.getKstj()), TradeGridStrategyDO::getKstj, dto.getKstj());
        query.eq(StringUtils.isNotEmpty(dto.getTztj()), TradeGridStrategyDO::getTztj, dto.getTztj());
        query.eq(StringUtils.isNotEmpty(dto.getSftzsmc()), TradeGridStrategyDO::getSftzsmc, dto.getSftzsmc());
        query.eq(Objects.nonNull(dto.getZyjg()), TradeGridStrategyDO::getZyjg, dto.getZyjg());
        query.eq(Objects.nonNull(dto.getZsjg()), TradeGridStrategyDO::getZsjg, dto.getZsjg());
        query.eq(StringUtils.isNotEmpty(dto.getKssj()), TradeGridStrategyDO::getKssj, dto.getKssj());
        query.eq(StringUtils.isNotEmpty(dto.getSfzjwg()), TradeGridStrategyDO::getSfzjwg, dto.getSfzjwg());
        return query;
    }

    @Override
    public TradeGridStrategyDTO convertToDTO(TradeGridStrategyDO tradeGridStrategyDO) {
        return convert.convert(tradeGridStrategyDO);
    }

    @Override
    public TradeGridStrategyDO convertToDO(TradeGridStrategyDTO tradeGridStrategyDTO) {
        return convert.convertToDO(tradeGridStrategyDTO);
    }
}