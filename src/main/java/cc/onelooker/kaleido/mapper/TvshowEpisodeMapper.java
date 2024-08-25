package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.TvshowEpisodeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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