package cc.onelooker.kaleido.mapper.movie;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.movie.MovieBasicActorDO;

/**
 * 电影演职员关联表Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Mapper
public interface MovieBasicActorMapper extends BaseMapper<MovieBasicActorDO> {

}