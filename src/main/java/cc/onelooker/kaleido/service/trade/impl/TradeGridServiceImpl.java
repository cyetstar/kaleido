package cc.onelooker.kaleido.service.trade.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.trade.TradeGridService;
import cc.onelooker.kaleido.entity.trade.TradeGridDO;
import cc.onelooker.kaleido.dto.trade.TradeGridDTO;
import cc.onelooker.kaleido.convert.trade.TradeGridConvert;
import cc.onelooker.kaleido.mapper.trade.TradeGridMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;

/**
 * 交易网格ServiceImpl
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
@Service
public class TradeGridServiceImpl extends AbstractBaseServiceImpl<TradeGridMapper, TradeGridDO, TradeGridDTO> implements TradeGridService {

    TradeGridConvert convert = TradeGridConvert.INSTANCE;

    @Override
    protected Wrapper<TradeGridDO> genQueryWrapper(TradeGridDTO dto) {
        LambdaQueryWrapper<TradeGridDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getSpdm()), TradeGridDO::getSpdm, dto.getSpdm());
        query.eq(Objects.nonNull(dto.getStrategId()), TradeGridDO::getStrategId, dto.getStrategId());
        query.eq(Objects.nonNull(dto.getRcjg()), TradeGridDO::getRcjg, dto.getRcjg());
        query.eq(Objects.nonNull(dto.getCcjg()), TradeGridDO::getCcjg, dto.getCcjg());
        query.eq(Objects.nonNull(dto.getTlcs()), TradeGridDO::getTlcs, dto.getTlcs());
        query.eq(Objects.nonNull(dto.getTlje()), TradeGridDO::getTlje, dto.getTlje());
        query.eq(StringUtils.isNotEmpty(dto.getSfzc()), TradeGridDO::getSfzc, dto.getSfzc());
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
}