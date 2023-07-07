package cc.onelooker.kaleido.service.trade.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.trade.TradeOrderService;
import cc.onelooker.kaleido.entity.trade.TradeOrderDO;
import cc.onelooker.kaleido.dto.trade.TradeOrderDTO;
import cc.onelooker.kaleido.convert.trade.TradeOrderConvert;
import cc.onelooker.kaleido.mapper.trade.TradeOrderMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 交易订单ServiceImpl
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
@Service
public class TradeOrderServiceImpl extends AbstractBaseServiceImpl<TradeOrderMapper, TradeOrderDO, TradeOrderDTO> implements TradeOrderService {

    TradeOrderConvert convert = TradeOrderConvert.INSTANCE;

    @Override
    protected Wrapper<TradeOrderDO> genQueryWrapper(TradeOrderDTO dto) {
        LambdaQueryWrapper<TradeOrderDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getSpdm()), TradeOrderDO::getSpdm, dto.getSpdm());
        query.eq(Objects.nonNull(dto.getGridId()), TradeOrderDO::getGridId, dto.getGridId());
        query.eq(StringUtils.isNotEmpty(dto.getJysddh()), TradeOrderDO::getJysddh, dto.getJysddh());
        query.eq(Objects.nonNull(dto.getJg()), TradeOrderDO::getJg, dto.getJg());
        query.eq(Objects.nonNull(dto.getYssl()), TradeOrderDO::getYssl, dto.getYssl());
        query.eq(Objects.nonNull(dto.getYjsl()), TradeOrderDO::getYjsl, dto.getYjsl());
        query.eq(Objects.nonNull(dto.getLjddje()), TradeOrderDO::getLjddje, dto.getLjddje());
        query.eq(StringUtils.isNotEmpty(dto.getDdzt()), TradeOrderDO::getDdzt, dto.getDdzt());
        query.eq(StringUtils.isNotEmpty(dto.getDdsxfs()), TradeOrderDO::getDdsxfs, dto.getDdsxfs());
        query.eq(StringUtils.isNotEmpty(dto.getDdlx()), TradeOrderDO::getDdlx, dto.getDdlx());
        query.eq(StringUtils.isNotEmpty(dto.getDdfx()), TradeOrderDO::getDdfx, dto.getDdfx());
        query.eq(Objects.nonNull(dto.getZsjg()), TradeOrderDO::getZsjg, dto.getZsjg());
        query.eq(Objects.nonNull(dto.getBssl()), TradeOrderDO::getBssl, dto.getBssl());
        query.eq(StringUtils.isNotEmpty(dto.getDdsj()), TradeOrderDO::getDdsj, dto.getDdsj());
        query.eq(StringUtils.isNotEmpty(dto.getGxsj()), TradeOrderDO::getGxsj, dto.getGxsj());
        query.eq(Objects.nonNull(dto.getYsjyje()), TradeOrderDO::getYsjyje, dto.getYsjyje());
        return query;
    }

    @Override
    public TradeOrderDTO convertToDTO(TradeOrderDO tradeOrderDO) {
        return convert.convert(tradeOrderDO);
    }

    @Override
    public TradeOrderDO convertToDO(TradeOrderDTO tradeOrderDTO) {
        return convert.convertToDO(tradeOrderDTO);
    }
}