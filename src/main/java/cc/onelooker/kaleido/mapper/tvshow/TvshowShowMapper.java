package cc.onelooker.kaleido.mapper.tvshow;

import cc.onelooker.kaleido.entity.tvshow.TvshowShowDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 剧集Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Mapper
public interface TvshowShowMapper extends BaseMapper<TvshowShowDO> {

    Long findMaxUpdatedAt();
}