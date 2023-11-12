package cc.onelooker.kaleido.mapper.movie;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.movie.MovieSetDO;

import java.lang.Long;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 电影集合Mapper接口
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Mapper
public interface MovieSetMapper extends BaseMapper<MovieSetDO> {

    List<MovieSetDO> listByMovieId(@Param("movieId") Long movieId);
}