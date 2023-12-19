package cc.onelooker.kaleido.mapper.music;

import cc.onelooker.kaleido.entity.music.MusicArtistAlbumDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 艺术家专辑关联表Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Mapper
public interface MusicArtistAlbumMapper extends BaseMapper<MusicArtistAlbumDO> {

}