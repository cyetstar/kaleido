package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.convert.movie.MovieCountryConvert;
import cc.onelooker.kaleido.dto.movie.MovieCountryDTO;
import cc.onelooker.kaleido.entity.movie.MovieCountryDO;
import cc.onelooker.kaleido.mapper.movie.MovieCountryMapper;
import cc.onelooker.kaleido.service.DictionaryBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieCountryService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

/**
 * 国家地区ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieCountryServiceImpl extends DictionaryBaseServiceImpl<MovieCountryMapper, MovieCountryDO, MovieCountryDTO> implements MovieCountryService {

    MovieCountryConvert convert = MovieCountryConvert.INSTANCE;

    @Override
    protected Wrapper<MovieCountryDO> genQueryWrapper(MovieCountryDTO dto) {
        LambdaQueryWrapper<MovieCountryDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getTag()), MovieCountryDO::getTag, dto.getTag());
        return query;
    }

    @Override
    public MovieCountryDTO convertToDTO(MovieCountryDO movieCountryDO) {
        return convert.convert(movieCountryDO);
    }

    @Override
    public MovieCountryDO convertToDO(MovieCountryDTO movieCountryDTO) {
        return convert.convertToDO(movieCountryDTO);
    }

    @Override
    public MovieCountryDTO findByTag(String tag) {
        Validate.notNull(tag);
        MovieCountryDTO param = new MovieCountryDTO();
        param.setTag(tag);
        return find(param);
    }

    @Override
    public MovieCountryDTO insert(Long id, String tag) {
        MovieCountryDTO movieCountryDTO = new MovieCountryDTO();
        movieCountryDTO.setId(id);
        movieCountryDTO.setTag(tag);
        return insert(movieCountryDTO);
    }
}