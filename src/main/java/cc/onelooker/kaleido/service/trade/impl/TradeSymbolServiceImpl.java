package cc.onelooker.kaleido.service.trade.impl;

import cc.onelooker.kaleido.convert.trade.TradeSymbolConvert;
import cc.onelooker.kaleido.dto.trade.TradeSymbolDTO;
import cc.onelooker.kaleido.entity.trade.TradeSymbolDO;
import cc.onelooker.kaleido.mapper.trade.TradeSymbolMapper;
import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import cc.onelooker.kaleido.service.trade.TradeSymbolService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 交易商品ServiceImpl
 *
 * @author cyetstar
 * @date 2023-07-06 11:56:08
 */
@Service
public class TradeSymbolServiceImpl extends KaleidoBaseServiceImpl<TradeSymbolMapper, TradeSymbolDO, TradeSymbolDTO> implements TradeSymbolService {

    TradeSymbolConvert convert = TradeSymbolConvert.INSTANCE;

    @Override
    protected Wrapper<TradeSymbolDO> genQueryWrapper(TradeSymbolDTO dto) {
        LambdaQueryWrapper<TradeSymbolDO> query = new LambdaQueryWrapper<>();
        query.like(StringUtils.isNotEmpty(dto.getSpmc()), TradeSymbolDO::getSpmc, StringUtils.upperCase(dto.getSpmc()));
        query.eq(StringUtils.isNotEmpty(dto.getJys()), TradeSymbolDO::getJys, dto.getJys());
        query.eq(StringUtils.isNotEmpty(dto.getJyb()), TradeSymbolDO::getJyb, dto.getJyb());
        query.eq(Objects.nonNull(dto.getJybjd()), TradeSymbolDO::getJybjd, dto.getJybjd());
        query.eq(Objects.nonNull(dto.getJybsxfjd()), TradeSymbolDO::getJybsxfjd, dto.getJybsxfjd());
        query.eq(StringUtils.isNotEmpty(dto.getJjb()), TradeSymbolDO::getJjb, dto.getJjb());
        query.eq(Objects.nonNull(dto.getJjbjgjd()), TradeSymbolDO::getJjbjgjd, dto.getJjbjgjd());
        query.eq(Objects.nonNull(dto.getJjbzcjd()), TradeSymbolDO::getJjbzcjd, dto.getJjbzcjd());
        query.eq(Objects.nonNull(dto.getJjbsxfjd()), TradeSymbolDO::getJjbsxfjd, dto.getJjbsxfjd());
        query.eq(StringUtils.isNotEmpty(dto.getSfyxsjwt()), TradeSymbolDO::getSfyxsjwt, dto.getSfyxsjwt());
        query.eq(StringUtils.isNotEmpty(dto.getSfyxapixhjy()), TradeSymbolDO::getSfyxapixhjy, dto.getSfyxapixhjy());
        query.eq(StringUtils.isNotEmpty(dto.getSfyxapiggjy()), TradeSymbolDO::getSfyxapiggjy, dto.getSfyxapiggjy());
        query.eq(StringUtils.isNotEmpty(dto.getZcddlx()), TradeSymbolDO::getZcddlx, dto.getZcddlx());
        query.eq(StringUtils.isNotEmpty(dto.getQx()), TradeSymbolDO::getQx, dto.getQx());
        query.eq(StringUtils.isNotEmpty(dto.getZdwtsl()), TradeSymbolDO::getZdwtsl, dto.getZdwtsl());
        query.eq(StringUtils.isNotEmpty(dto.getMarkersxf()), TradeSymbolDO::getMarkersxf, dto.getMarkersxf());
        query.eq(StringUtils.isNotEmpty(dto.getTakersxf()), TradeSymbolDO::getTakersxf, dto.getTakersxf());
        query.eq(StringUtils.isNotEmpty(dto.getZxxdje()), TradeSymbolDO::getZxxdje, dto.getZxxdje());
        query.eq(StringUtils.isNotEmpty(dto.getZxxdsl()), TradeSymbolDO::getZxxdsl, dto.getZxxdsl());
        query.eq(StringUtils.isNotEmpty(dto.getSfsc()), TradeSymbolDO::getSfsc, dto.getSfsc());
        return query;
    }

    @Override
    public TradeSymbolDTO convertToDTO(TradeSymbolDO tradeSymbolDO) {
        return convert.convert(tradeSymbolDO);
    }

    @Override
    public TradeSymbolDO convertToDO(TradeSymbolDTO tradeSymbolDTO) {
        return convert.convertToDO(tradeSymbolDTO);
    }

    @Override
    @Transactional
    public void batchSave(List<String> spmcList, String jys) {
        for (String spmc : spmcList) {
            TradeSymbolDTO tradeSymbolDTO = findBySpmcAndJys(spmc, jys);
            if (tradeSymbolDTO != null) {
                continue;
            }
            tradeSymbolDTO = new TradeSymbolDTO();
            tradeSymbolDTO.setSpmc(spmc);
            tradeSymbolDTO.setJys(jys);
            insert(tradeSymbolDTO);
        }
    }

    @Override
    @Transactional
    public void like(Long id, String sfsc) {
        TradeSymbolDO tradeSymbolDO = new TradeSymbolDO();
        tradeSymbolDO.setId(id);
        tradeSymbolDO.setSfsc(sfsc);
        baseMapper.updateById(tradeSymbolDO);
    }

    private TradeSymbolDTO findBySpmcAndJys(String spmc, String jys) {
        TradeSymbolDTO param = new TradeSymbolDTO();
        param.setSpmc(spmc);
        param.setJys(jys);
        return find(param);
    }

}