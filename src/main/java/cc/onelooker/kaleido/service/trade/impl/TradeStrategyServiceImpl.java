package cc.onelooker.kaleido.service.trade.impl;

import cc.onelooker.kaleido.convert.trade.TradeStrategyConvert;
import cc.onelooker.kaleido.dto.trade.TradeGridDTO;
import cc.onelooker.kaleido.dto.trade.TradeOrderDTO;
import cc.onelooker.kaleido.dto.trade.TradeStrategyDTO;
import cc.onelooker.kaleido.dto.trade.TradeSymbolDTO;
import cc.onelooker.kaleido.entity.trade.TradeStrategyDO;
import cc.onelooker.kaleido.mapper.trade.TradeStrategyMapper;
import cc.onelooker.kaleido.service.trade.TradeGridService;
import cc.onelooker.kaleido.service.trade.TradeOrderService;
import cc.onelooker.kaleido.service.trade.TradeStrategyService;
import cc.onelooker.kaleido.service.trade.TradeSymbolService;
import cc.onelooker.kaleido.utils.BigDecimalUtils;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import com.zjjcnt.common.util.DateTimeUtils;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 策略ServiceImpl
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */
@Service
public class TradeStrategyServiceImpl extends AbstractBaseServiceImpl<TradeStrategyMapper, TradeStrategyDO, TradeStrategyDTO> implements TradeStrategyService {

    TradeStrategyConvert convert = TradeStrategyConvert.INSTANCE;

    @Autowired
    private TradeGridService tradeGridService;

    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private TradeSymbolService tradeSymbolService;

    @Override
    protected Wrapper<TradeStrategyDO> genQueryWrapper(TradeStrategyDTO dto) {
        LambdaQueryWrapper<TradeStrategyDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getSymbolId()), TradeStrategyDO::getSymbolId, dto.getSymbolId());
        query.eq(StringUtils.isNotEmpty(dto.getCjsj()), TradeStrategyDO::getCjsj, dto.getCjsj());
        query.eq(StringUtils.isNotEmpty(dto.getXgsj()), TradeStrategyDO::getXgsj, dto.getXgsj());
        query.eq(StringUtils.isNotEmpty(dto.getJyfx()), TradeStrategyDO::getJyfx, dto.getJyfx());
        query.eq(StringUtils.isNotEmpty(dto.getQjzgz()), TradeStrategyDO::getQjzgz, dto.getQjzgz());
        query.eq(StringUtils.isNotEmpty(dto.getQjzdz()), TradeStrategyDO::getQjzdz, dto.getQjzdz());
        query.eq(StringUtils.isNotEmpty(dto.getWgms()), TradeStrategyDO::getWgms, dto.getWgms());
        query.eq(Objects.nonNull(dto.getWgsl()), TradeStrategyDO::getWgsl, dto.getWgsl());
        query.eq(StringUtils.isNotEmpty(dto.getTrje()), TradeStrategyDO::getTrje, dto.getTrje());
        query.eq(StringUtils.isNotEmpty(dto.getQjsysx()), TradeStrategyDO::getQjsysx, dto.getQjsysx());
        query.eq(StringUtils.isNotEmpty(dto.getQjxyxx()), TradeStrategyDO::getQjxyxx, dto.getQjxyxx());
        query.eq(StringUtils.isNotEmpty(dto.getSfqjsy()), TradeStrategyDO::getSfqjsy, dto.getSfqjsy());
        query.eq(StringUtils.isNotEmpty(dto.getSfqjxy()), TradeStrategyDO::getSfqjxy, dto.getSfqjxy());
        query.eq(StringUtils.isNotEmpty(dto.getKstj()), TradeStrategyDO::getKstj, dto.getKstj());
        query.eq(StringUtils.isNotEmpty(dto.getTztj()), TradeStrategyDO::getTztj, dto.getTztj());
        query.eq(StringUtils.isNotEmpty(dto.getSftzsmc()), TradeStrategyDO::getSftzsmc, dto.getSftzsmc());
        query.eq(StringUtils.isNotEmpty(dto.getZyjg()), TradeStrategyDO::getZyjg, dto.getZyjg());
        query.eq(StringUtils.isNotEmpty(dto.getZsjg()), TradeStrategyDO::getZsjg, dto.getZsjg());
        query.eq(StringUtils.isNotEmpty(dto.getKssj()), TradeStrategyDO::getKssj, dto.getKssj());
        query.eq(StringUtils.isNotEmpty(dto.getSfzjwg()), TradeStrategyDO::getSfzjwg, dto.getSfzjwg());
        return query;
    }

    @Override
    public TradeStrategyDTO convertToDTO(TradeStrategyDO tradeStrategyDO) {
        return convert.convert(tradeStrategyDO);
    }

    @Override
    public TradeStrategyDO convertToDO(TradeStrategyDTO tradeStrategyDTO) {
        return convert.convertToDO(tradeStrategyDTO);
    }

    @Override
    @Transactional
    public TradeStrategyDTO insert(TradeStrategyDTO dto) {
        TradeSymbolDTO tradeSymbolDTO = tradeSymbolService.findById(dto.getSymbolId());
        dto.setWgje(BigDecimalUtils.divide(dto.getTrje(), String.valueOf(dto.getWgsl()), tradeSymbolDTO.getJjbjgjd()));
        dto.setSfyx(Constants.NO);
        dto = super.insert(dto);
        createTradeGrid(dto);
        return dto;
    }

    @Override
    @Transactional
    public void start(Long id) {
        TradeStrategyDO entity = new TradeStrategyDO();
        entity.setId(id);
        entity.setKssj(DateTimeUtils.now());
        entity.setSfyx(Constants.YES);
        baseMapper.updateById(entity);
        TradeStrategyDTO dto = findById(id);
        createTradeOrder(dto);
    }

    @Override
    @Transactional
    public void stop(Long id) {

    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        tradeGridService.deleteByStrategyId((Long) id);
        return super.deleteById(id);
    }

    private void createTradeGrid(TradeStrategyDTO tradeStrategyDTO) {
        BigDecimal high = new BigDecimal(tradeStrategyDTO.getQjzgz());
        BigDecimal low = new BigDecimal(tradeStrategyDTO.getQjzdz());
        Integer number = tradeStrategyDTO.getWgsl();
        String mode = tradeStrategyDTO.getWgms();
        if (StringUtils.equals(mode, KaleidoConstants.WGMS_DC)) {
            BigDecimal len = (high.subtract(low)).divide(new BigDecimal(number));
            for (int i = 0; i < number; i++) {
                TradeGridDTO tradeGridDTO = new TradeGridDTO();
                // low + len * i
                BigDecimal rcjg = low.add(len.multiply(new BigDecimal(i)));
                // low + len * (i+1)
                BigDecimal ccjg = low.add(len.multiply(new BigDecimal(i + 1)));
                tradeGridDTO.setWgje(tradeStrategyDTO.getWgje());
                tradeGridDTO.setRcjg(rcjg.toPlainString());
                tradeGridDTO.setCcjg(ccjg.toPlainString());
                tradeGridDTO.setStrategyId(tradeStrategyDTO.getId());
                tradeGridDTO.setSymbolId(tradeStrategyDTO.getSymbolId());
                tradeGridService.insert(tradeGridDTO);
            }
        }
    }

    private void createTradeOrder(TradeStrategyDTO dto) {
        TradeSymbolDTO tradeSymbolDTO = tradeSymbolService.findById(dto.getSymbolId());
        List<TradeGridDTO> tradeGridDTOList = tradeGridService.listByStrategyId(dto.getId());
        for (TradeGridDTO tradeGridDTO : tradeGridDTOList) {
            TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
            tradeOrderDTO.setGridId(tradeGridDTO.getId());
            tradeOrderDTO.setSymbolId(tradeGridDTO.getSymbolId());
            tradeOrderDTO.setDdlx(KaleidoConstants.DDLX_LIMIT);
            tradeOrderDTO.setDdfx(dto.getJyfx());
            tradeOrderDTO.setJg(tradeGridDTO.getRcjg());
            tradeOrderDTO.setYsjyje(dto.getWgje());
            tradeOrderDTO.setYssl(BigDecimalUtils.divide(tradeOrderDTO.getYsjyje(), tradeOrderDTO.getJg(), tradeSymbolDTO.getZxxdsljd()));
            tradeOrderDTO.setZsjg(dto.getZsjg());
            tradeOrderDTO.setDdsj(DateTimeUtils.now());
            tradeOrderDTO.setGxsj(DateTimeUtils.now());
            tradeOrderDTO.setDdzt(KaleidoConstants.DDZT_NEW);
            tradeOrderDTO.setFsbz(KaleidoConstants.FSBZ_DFS);
            tradeOrderService.insert(tradeOrderDTO);
        }
    }
}