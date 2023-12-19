package cc.onelooker.kaleido.service.trade.impl;

import cc.onelooker.kaleido.convert.trade.TradeLogConvert;
import cc.onelooker.kaleido.dto.trade.TradeAccountDTO;
import cc.onelooker.kaleido.dto.trade.TradeLogDTO;
import cc.onelooker.kaleido.entity.trade.TradeLogDO;
import cc.onelooker.kaleido.mapper.trade.TradeLogMapper;
import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import cc.onelooker.kaleido.service.trade.TradeAccountService;
import cc.onelooker.kaleido.service.trade.TradeLogService;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 交易记录ServiceImpl
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */
@Service
public class TradeLogServiceImpl extends KaleidoBaseServiceImpl<TradeLogMapper, TradeLogDO, TradeLogDTO> implements TradeLogService {

    TradeLogConvert convert = TradeLogConvert.INSTANCE;

    @Autowired
    private TradeAccountService tradeAccountService;

    @Override
    protected Wrapper<TradeLogDO> genQueryWrapper(TradeLogDTO dto) {
        LambdaQueryWrapper<TradeLogDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getSymbolId()), TradeLogDO::getSymbolId, dto.getSymbolId());
        query.eq(Objects.nonNull(dto.getAccountId()), TradeLogDO::getAccountId, dto.getAccountId());
        query.eq(StringUtils.isNotEmpty(dto.getJyfx()), TradeLogDO::getJyfx, dto.getJyfx());
        query.eq(StringUtils.isNotEmpty(dto.getSfjg()), TradeLogDO::getSfjg, dto.getSfjg());
        query.eq(StringUtils.isNotEmpty(dto.getSjzq()), TradeLogDO::getSjzq, dto.getSjzq());
        query.eq(StringUtils.isNotEmpty(dto.getRcjg()), TradeLogDO::getRcjg, dto.getRcjg());
        query.eq(StringUtils.isNotEmpty(dto.getCcjg()), TradeLogDO::getCcjg, dto.getCcjg());
        query.eq(StringUtils.isNotEmpty(dto.getTcgm()), TradeLogDO::getTcgm, dto.getTcgm());
        query.eq(StringUtils.isNotEmpty(dto.getYkje()), TradeLogDO::getYkje, dto.getYkje());
        return query;
    }

    @Override
    public TradeLogDTO convertToDTO(TradeLogDO tradeLogDO) {
        return convert.convert(tradeLogDO);
    }

    @Override
    public TradeLogDO convertToDO(TradeLogDTO tradeLogDTO) {
        return convert.convertToDO(tradeLogDTO);
    }

    @Override
    @Transactional
    public TradeLogDTO insert(TradeLogDTO dto) {
        if (new BigDecimal(dto.getYkje()).compareTo(BigDecimal.ZERO) > 0) {
            dto.setSfjg(KaleidoConstants.SFJG_WIN);
        } else {
            dto.setSfjg(KaleidoConstants.SFJG_LOSS);
        }
        TradeAccountDTO tradeAccountDTO = tradeAccountService.findById(dto.getAccountId());
        BigDecimal dqye = new BigDecimal(tradeAccountDTO.getDqye());
        dqye = dqye.add(new BigDecimal(dto.getYkje()));
        tradeAccountDTO.setDqye(dqye.toPlainString());
        tradeAccountService.update(tradeAccountDTO);
        return super.insert(dto);
    }

    @Override
    @Transactional
    public boolean update(TradeLogDTO dto) {
        if (new BigDecimal(dto.getYkje()).compareTo(BigDecimal.ZERO) > 0) {
            dto.setSfjg(KaleidoConstants.SFJG_WIN);
        } else {
            dto.setSfjg(KaleidoConstants.SFJG_LOSS);
        }
        TradeLogDTO sourceDTO = findById(dto.getId());
        TradeAccountDTO tradeAccountDTO = tradeAccountService.findById(dto.getAccountId());
        BigDecimal dqye = new BigDecimal(tradeAccountDTO.getDqye());
        dqye = dqye.subtract(new BigDecimal(sourceDTO.getYkje()));
        dqye = dqye.add(new BigDecimal(dto.getYkje()));
        tradeAccountDTO.setDqye(dqye.toPlainString());
        tradeAccountService.update(tradeAccountDTO);
        return super.update(dto);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        TradeLogDTO sourceDTO = findById(id);
        TradeAccountDTO tradeAccountDTO = tradeAccountService.findById(sourceDTO.getAccountId());
        BigDecimal dqye = new BigDecimal(tradeAccountDTO.getDqye());
        dqye = dqye.subtract(new BigDecimal(sourceDTO.getYkje()));
        tradeAccountDTO.setDqye(dqye.toPlainString());
        tradeAccountService.update(tradeAccountDTO);
        return super.deleteById(id);
    }
}