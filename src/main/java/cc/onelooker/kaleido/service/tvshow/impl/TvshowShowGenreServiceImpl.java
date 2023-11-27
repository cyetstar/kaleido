package cc.onelooker.kaleido.service.tvshow.impl;

import cc.onelooker.kaleido.convert.tvshow.TvshowShowGenreConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowShowGenreDTO;
import cc.onelooker.kaleido.entity.tvshow.TvshowShowGenreDO;
import cc.onelooker.kaleido.mapper.tvshow.TvshowShowGenreMapper;
import cc.onelooker.kaleido.service.tvshow.TvshowShowGenreService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 剧集类型关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Service
public class TvshowShowGenreServiceImpl extends AbstractBaseServiceImpl<TvshowShowGenreMapper, TvshowShowGenreDO, TvshowShowGenreDTO> implements TvshowShowGenreService {

    TvshowShowGenreConvert convert = TvshowShowGenreConvert.INSTANCE;

    @Override
    protected Wrapper<TvshowShowGenreDO> genQueryWrapper(TvshowShowGenreDTO dto) {
        LambdaQueryWrapper<TvshowShowGenreDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getShowId()), TvshowShowGenreDO::getShowId, dto.getShowId());
        query.eq(Objects.nonNull(dto.getGenreId()), TvshowShowGenreDO::getGenreId, dto.getGenreId());
        return query;
    }

    @Override
    public TvshowShowGenreDTO convertToDTO(TvshowShowGenreDO tvshowShowGenreDO) {
        return convert.convert(tvshowShowGenreDO);
    }

    @Override
    public TvshowShowGenreDO convertToDO(TvshowShowGenreDTO tvshowShowGenreDTO) {
        return convert.convertToDO(tvshowShowGenreDTO);
    }

    @Override
    public TvshowShowGenreDTO findByShowIdAndGenreId(Long showId, Long genreId) {
        Validate.notNull(showId);
        Validate.notNull(genreId);
        TvshowShowGenreDTO param = new TvshowShowGenreDTO();
        param.setShowId(showId);
        param.setGenreId(genreId);
        return find(param);
    }

    @Override
    public TvshowShowGenreDTO insert(Long showId, Long genreId) {
        TvshowShowGenreDTO tvshowShowGenreDTO = new TvshowShowGenreDTO();
        tvshowShowGenreDTO.setShowId(showId);
        tvshowShowGenreDTO.setGenreId(genreId);
        return insert(tvshowShowGenreDTO);
    }
}