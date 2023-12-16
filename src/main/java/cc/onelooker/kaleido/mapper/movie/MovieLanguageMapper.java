package cc.onelooker.kaleido.mapper.movie;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.movie.MovieLanguageDO;

/**
 * 语言Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Mapper
public interface MovieLanguageMapper extends BaseMapper<MovieLanguageDO> {

}