package cc.onelooker.kaleido.mapper.business;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.business.MovieCountryDO;
import org.apache.ibatis.annotations.Param;

import java.lang.Long;
import java.lang.String;
import java.util.List;

/**
 * 国家地区Mapper接口
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Mapper
public interface MovieCountryMapper extends BaseMapper<MovieCountryDO> {

    List<MovieCountryDO> listByMovieId(@Param("movieId") Long movieId);
}