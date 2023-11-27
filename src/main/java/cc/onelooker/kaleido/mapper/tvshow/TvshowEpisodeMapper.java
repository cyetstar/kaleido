package cc.onelooker.kaleido.mapper.tvshow;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.tvshow.TvshowEpisodeDO;

/**
 * 单集Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Mapper
public interface TvshowEpisodeMapper extends BaseMapper<TvshowEpisodeDO> {

    Long findMaxUpdatedAt();

}