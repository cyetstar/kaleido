package cc.onelooker.kaleido.mapper.music;

import cc.onelooker.kaleido.entity.music.MusicAlbumDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 专辑Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Mapper
public interface MusicAlbumMapper extends BaseMapper<MusicAlbumDO> {

    Long findMaxUpdatedAt();

}