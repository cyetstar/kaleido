package cc.onelooker.kaleido.mapper.movie;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.movie.MovieDoubanWeeklyDO;

/**
 * 豆瓣电影口碑榜Mapper接口
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */
@Mapper
public interface MovieDoubanWeeklyMapper extends BaseMapper<MovieDoubanWeeklyDO> {

}