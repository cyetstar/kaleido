package cc.onelooker.kaleido.mapper.movie;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.movie.MovieFileSubtitleDO;

/**
 * 电影文件字幕信息Mapper接口
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 */
@Mapper
public interface MovieFileSubtitleMapper extends BaseMapper<MovieFileSubtitleDO> {

}