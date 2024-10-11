package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.ActorDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 演职员Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Mapper
public interface ActorMapper extends BaseMapper<ActorDO> {

    void deleteBySeasonIdAndRole(@Param("seasonId") String seasonId, @Param("role") String role);

    void deleteByMovieIdAndRole(@Param("movieId") String movieId, @Param("role") String role);
}