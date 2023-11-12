package cc.onelooker.kaleido.mapper.music;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.music.MusicReleaseDO;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;


/**
 * 发行品Mapper接口
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */
@Mapper
public interface MusicReleaseMapper extends BaseMapper<MusicReleaseDO> {

}