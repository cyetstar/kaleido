package cc.onelooker.kaleido.mapper.music;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.music.MusicTrackDO;

/**
 * 曲目Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Mapper
public interface MusicTrackMapper extends BaseMapper<MusicTrackDO> {

}