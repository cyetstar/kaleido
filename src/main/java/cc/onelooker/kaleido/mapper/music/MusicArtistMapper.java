package cc.onelooker.kaleido.mapper.music;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.music.MusicArtistDO;

/**
 * 艺术家Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Mapper
public interface MusicArtistMapper extends BaseMapper<MusicArtistDO> {

}