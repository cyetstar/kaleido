package cc.onelooker.kaleido.service.trade.impl;

import cc.onelooker.kaleido.service.ExBaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.trade.TradeAccountService;
import cc.onelooker.kaleido.entity.trade.TradeAccountDO;
import cc.onelooker.kaleido.dto.trade.TradeAccountDTO;
import cc.onelooker.kaleido.convert.trade.TradeAccountConvert;
import cc.onelooker.kaleido.mapper.trade.TradeAccountMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 交易账户ServiceImpl
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */
@Service
public class TradeAccountServiceImpl extends ExBaseServiceImpl<TradeAccountMapper, TradeAccountDO, TradeAccountDTO> implements TradeAccountService {

    TradeAccountConvert convert = TradeAccountConvert.INSTANCE;

    @Override
    protected Wrapper<TradeAccountDO> genQueryWrapper(TradeAccountDTO dto) {
        LambdaQueryWrapper<TradeAccountDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getZhmc()), TradeAccountDO::getZhmc, dto.getZhmc());
        query.eq(Objects.nonNull(dto.getCsye()), TradeAccountDO::getCsye, dto.getCsye());
        query.eq(Objects.nonNull(dto.getDqye()), TradeAccountDO::getDqye, dto.getDqye());
        query.eq(StringUtils.isNotEmpty(dto.getCjsj()), TradeAccountDO::getCjsj, dto.getCjsj());
        query.eq(StringUtils.isNotEmpty(dto.getXgsj()), TradeAccountDO::getXgsj, dto.getXgsj());
        return query;
    }

    @Override
    public TradeAccountDTO convertToDTO(TradeAccountDO tradeAccountDO) {
        return convert.convert(tradeAccountDO);
    }

    @Override
    public TradeAccountDO convertToDO(TradeAccountDTO tradeAccountDTO) {
        return convert.convertToDO(tradeAccountDTO);
    }
}