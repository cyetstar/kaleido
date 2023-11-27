package cc.onelooker.kaleido.service.tvshow.impl;

import cc.onelooker.kaleido.convert.tvshow.TvshowGenreConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowGenreDTO;
import cc.onelooker.kaleido.entity.tvshow.TvshowGenreDO;
import cc.onelooker.kaleido.mapper.tvshow.TvshowGenreMapper;
import cc.onelooker.kaleido.service.tvshow.TvshowGenreService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 剧集类型ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Service
public class TvshowGenreServiceImpl extends AbstractBaseServiceImpl<TvshowGenreMapper, TvshowGenreDO, TvshowGenreDTO> implements TvshowGenreService {

    TvshowGenreConvert convert = TvshowGenreConvert.INSTANCE;

    @Override
    protected Wrapper<TvshowGenreDO> genQueryWrapper(TvshowGenreDTO dto) {
        LambdaQueryWrapper<TvshowGenreDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getTag()), TvshowGenreDO::getTag, dto.getTag());
        return query;
    }

    @Override
    public TvshowGenreDTO convertToDTO(TvshowGenreDO tvshowGenreDO) {
        return convert.convert(tvshowGenreDO);
    }

    @Override
    public TvshowGenreDO convertToDO(TvshowGenreDTO tvshowGenreDTO) {
        return convert.convertToDO(tvshowGenreDTO);
    }

    @Override
    public TvshowGenreDTO insert(Long id, String tag) {
        TvshowGenreDTO tvshowGenreDTO = new TvshowGenreDTO();
        tvshowGenreDTO.setId(id);
        tvshowGenreDTO.setTag(tag);
        return insert(tvshowGenreDTO);
    }
}