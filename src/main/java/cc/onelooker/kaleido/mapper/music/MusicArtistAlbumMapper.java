package cc.onelooker.kaleido.mapper.music;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.music.MusicArtistAlbumDO;

/**
 * 艺术家专辑关联表Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Mapper
public interface MusicArtistAlbumMapper extends BaseMapper<MusicArtistAlbumDO> {

}