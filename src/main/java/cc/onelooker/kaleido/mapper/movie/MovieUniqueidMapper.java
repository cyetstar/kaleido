package cc.onelooker.kaleido.mapper.movie;

import cc.onelooker.kaleido.entity.movie.MovieDO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.movie.MovieUniqueidDO;

import java.lang.String;

import org.apache.ibatis.annotations.Param;

/**
 * 电影唯一标识Mapper接口
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Mapper
public interface MovieUniqueidMapper extends BaseMapper<MovieUniqueidDO> {

    MovieDO findByUidAndBslx(@Param("uid") String uid, @Param("bslx") String bslx);
}