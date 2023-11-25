package cc.onelooker.kaleido.mapper.movie;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.movie.MovieCollectionDO;

/**
 * 电影集合Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Mapper
public interface MovieCollectionMapper extends BaseMapper<MovieCollectionDO> {

}