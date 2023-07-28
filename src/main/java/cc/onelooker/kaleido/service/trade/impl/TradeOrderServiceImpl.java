package cc.onelooker.kaleido.service.trade.impl;

import cc.onelooker.kaleido.dto.trade.TradeGridDTO;
import cc.onelooker.kaleido.dto.trade.TradeStrategyDTO;
import cc.onelooker.kaleido.service.trade.TradeGridService;
import cc.onelooker.kaleido.service.trade.TradeStrategyService;
import cc.onelooker.kaleido.utils.BigDecimalUtils;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.util.DateTimeUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * 交易订单ServiceImpl
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
@Service
public class TradeOrderServiceImpl extends AbstractBaseServiceImpl<TradeOrderMapper, TradeOrderDO, TradeOrderDTO> implements TradeOrderService {

    TradeOrderConvert convert = TradeOrderConvert.INSTANCE;

    @Autowired
    private TradeGridService tradeGridService;

    @Autowired
    private TradeStrategyService tradeStrategyService;

    @Override
    protected Wrapper<TradeOrderDO> genQueryWrapper(TradeOrderDTO dto) {
        LambdaQueryWrapper<TradeOrderDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getSymbolId()), TradeOrderDO::getSymbolId, dto.getSymbolId());
        query.eq(Objects.nonNull(dto.getStrategyId()), TradeOrderDO::getStrategyId, dto.getStrategyId());
        query.eq(Objects.nonNull(dto.getGridId()), TradeOrderDO::getGridId, dto.getGridId());
        query.eq(StringUtils.isNotEmpty(dto.getJysddh()), TradeOrderDO::getJysddh, dto.getJysddh());
        query.eq(StringUtils.isNotEmpty(dto.getJg()), TradeOrderDO::getJg, dto.getJg());
        query.eq(StringUtils.isNotEmpty(dto.getYssl()), TradeOrderDO::getYssl, dto.getYssl());
        query.eq(StringUtils.isNotEmpty(dto.getJysl()), TradeOrderDO::getJysl, dto.getJysl());
        query.eq(StringUtils.isNotEmpty(dto.getLjddje()), TradeOrderDO::getLjddje, dto.getLjddje());
        query.eq(StringUtils.isNotEmpty(dto.getDdzt()), TradeOrderDO::getDdzt, dto.getDdzt());
        query.eq(StringUtils.isNotEmpty(dto.getDdsxfs()), TradeOrderDO::getDdsxfs, dto.getDdsxfs());
        query.eq(StringUtils.isNotEmpty(dto.getDdlx()), TradeOrderDO::getDdlx, dto.getDdlx());
        query.eq(StringUtils.isNotEmpty(dto.getDdfx()), TradeOrderDO::getDdfx, dto.getDdfx());
        query.eq(StringUtils.isNotEmpty(dto.getZsjg()), TradeOrderDO::getZsjg, dto.getZsjg());
        query.eq(Objects.nonNull(dto.getBssl()), TradeOrderDO::getBssl, dto.getBssl());
        query.eq(StringUtils.isNotEmpty(dto.getDdsj()), TradeOrderDO::getDdsj, dto.getDdsj());
        query.eq(StringUtils.isNotEmpty(dto.getGxsj()), TradeOrderDO::getGxsj, dto.getGxsj());
        query.eq(StringUtils.isNotEmpty(dto.getYsjyje()), TradeOrderDO::getYsjyje, dto.getYsjyje());
        query.eq(StringUtils.isNotEmpty(dto.getFsbz()), TradeOrderDO::getFsbz, dto.getFsbz());
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

    @Override
    public PageResult<TradeOrderDTO> pageByGridId(Long gridId, Page<TradeOrderDTO> page) {
        TradeOrderDTO param = new TradeOrderDTO();
        param.setGridId(gridId);
        return page(param, page);
    }

    @Override
    @Transactional
    public void deleteByGridId(Long gridId) {
        TradeOrderDTO param = new TradeOrderDTO();
        param.setGridId(gridId);
        delete(param);
    }

    @Override
    @Transactional
    public void updateStatus(TradeOrderDTO tradeOrderDTO) {
        update(tradeOrderDTO);
        TradeGridDTO tradeGridDTO = tradeGridService.findById(tradeOrderDTO.getGridId());
        TradeStrategyDTO tradeStrategyDTO = tradeStrategyService.findById(tradeOrderDTO.getStrategyId());
        if (StringUtils.equals(tradeStrategyDTO.getJyfx(), tradeOrderDTO.getDdfx()) && StringUtils.equals(tradeOrderDTO.getDdzt(), KaleidoConstants.DDZT_FILLED)) {
            //订单方向与策略相反时，套利成功
            BigDecimal ccjg = new BigDecimal(tradeGridDTO.getCcjg());
            BigDecimal rcjg = new BigDecimal(tradeGridDTO.getRcjg());
            BigDecimal ljddje = new BigDecimal(tradeOrderDTO.getLjddje());
            BigDecimal tlje = new BigDecimal(ObjectUtils.defaultIfNull(tradeGridDTO.getTlje(), "0"));
            tlje = tlje.add(ccjg.subtract(rcjg).divide(ccjg, 4).multiply(ljddje).abs());
            tradeGridDTO.setTlje(tlje.toPlainString());
            tradeGridDTO.setTlcs(ObjectUtils.defaultIfNull(tradeGridDTO.getTlcs(), 0) + 1);
        }
        if (StringUtils.equals(tradeOrderDTO.getDdfx(), KaleidoConstants.DDFX_BUY)) {
            createNewOrder(tradeGridDTO, KaleidoConstants.DDFX_SELL);
        } else {
            createNewOrder(tradeGridDTO, KaleidoConstants.DDFX_BUY);
        }
    }

    public void createNewOrder(TradeGridDTO tradeGridDTO, String ddfx) {
        TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
        tradeOrderDTO.setGridId(tradeGridDTO.getId());
        tradeOrderDTO.setSymbolId(tradeGridDTO.getSymbolId());
        tradeOrderDTO.setStrategyId(tradeGridDTO.getStrategyId());
        tradeOrderDTO.setDdlx(KaleidoConstants.DDLX_LIMIT);
        tradeOrderDTO.setDdfx(ddfx);
        tradeOrderDTO.setJg(tradeGridDTO.getRcjg());
        tradeOrderDTO.setYsjyje(tradeGridDTO.getWgje());
        tradeOrderDTO.setDdsj(DateTimeUtils.now());
        tradeOrderDTO.setGxsj(DateTimeUtils.now());
        tradeOrderDTO.setDdzt(KaleidoConstants.DDZT_NEW);
        tradeOrderDTO.setFsbz(KaleidoConstants.FSBZ_DFS);
        insert(tradeOrderDTO);
    }

    @Override
    public void order(Long id) {

    }
}