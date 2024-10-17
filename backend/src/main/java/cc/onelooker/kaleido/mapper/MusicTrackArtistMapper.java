package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.MusicTrackArtistDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 艺术家歌曲关联表Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Mapper
public interface MusicTrackArtistMapper extends BaseMapper<MusicTrackArtistDO> {

}