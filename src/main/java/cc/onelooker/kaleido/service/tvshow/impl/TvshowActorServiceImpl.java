package cc.onelooker.kaleido.service.tvshow.impl;

import cc.onelooker.kaleido.dto.tvshow.TvshowShowActorDTO;
import cc.onelooker.kaleido.service.tvshow.TvshowShowActorService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.tvshow.TvshowActorService;
import cc.onelooker.kaleido.entity.tvshow.TvshowActorDO;
import cc.onelooker.kaleido.dto.tvshow.TvshowActorDTO;
import cc.onelooker.kaleido.convert.tvshow.TvshowActorConvert;
import cc.onelooker.kaleido.mapper.tvshow.TvshowActorMapper;

import com.zjjcnt.common.core.utils.ColumnUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 剧集演职员ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Service
public class TvshowActorServiceImpl extends AbstractBaseServiceImpl<TvshowActorMapper, TvshowActorDO, TvshowActorDTO> implements TvshowActorService {

    TvshowActorConvert convert = TvshowActorConvert.INSTANCE;

    @Autowired
    private TvshowShowActorService tvshowShowActorService;

    @Override
    protected Wrapper<TvshowActorDO> genQueryWrapper(TvshowActorDTO dto) {
        LambdaQueryWrapper<TvshowActorDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getName()), TvshowActorDO::getName, dto.getName());
        query.eq(StringUtils.isNotEmpty(dto.getOriginalName()), TvshowActorDO::getOriginalName, dto.getOriginalName());
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), TvshowActorDO::getDoubanId, dto.getDoubanId());
        return query;
    }

    @Override
    public TvshowActorDTO convertToDTO(TvshowActorDO tvshowActorDO) {
        return convert.convert(tvshowActorDO);
    }

    @Override
    public TvshowActorDO convertToDO(TvshowActorDTO tvshowActorDTO) {
        return convert.convertToDO(tvshowActorDTO);
    }

    @Override
    public List<TvshowActorDTO> listByShowId(Long showId) {
        List<TvshowShowActorDTO> tvshowShowActorDTOList = tvshowShowActorService.listByShowId(showId);
        return tvshowShowActorDTOList.stream().map(s -> {
                    TvshowActorDTO tvshowActorDTO = findById(s.getActorId());
                    tvshowActorDTO.setRole(s.getRole());
                    tvshowActorDTO.setPlayRole(s.getPlayRole());
                    return tvshowActorDTO;
                })
                .collect(Collectors.toList());
    }
}