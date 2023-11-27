package cc.onelooker.kaleido.mapper.tvshow;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.tvshow.TvshowActorDO;

/**
 * 剧集演职员Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Mapper
public interface TvshowActorMapper extends BaseMapper<TvshowActorDO> {

}