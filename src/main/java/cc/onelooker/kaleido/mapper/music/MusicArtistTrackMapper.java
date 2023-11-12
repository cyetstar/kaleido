package cc.onelooker.kaleido.mapper.music;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.music.MusicArtistTrackDO;

import java.lang.Long;


/**
 * 艺术家曲目关联表Mapper接口
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */
@Mapper
public interface MusicArtistTrackMapper extends BaseMapper<MusicArtistTrackDO> {

}