package cc.onelooker.kaleido.mapper.tvshow;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.tvshow.TvshowEpisodeActorDO;

/**
 * 单集演职员关联表Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Mapper
public interface TvshowEpisodeActorMapper extends BaseMapper<TvshowEpisodeActorDO> {

}