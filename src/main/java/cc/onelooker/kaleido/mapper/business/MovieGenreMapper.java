package cc.onelooker.kaleido.mapper.business;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.business.MovieGenreDO;
import org.apache.ibatis.annotations.Param;

import java.lang.Long;
import java.lang.String;
import java.util.List;

/**
 * 影片类型Mapper接口
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Mapper
public interface MovieGenreMapper extends BaseMapper<MovieGenreDO> {

    List<MovieGenreDO> listByMovieId(@Param("movieId") Long movieId);
}