package cc.onelooker.kaleido.mapper.movie;

import cc.onelooker.kaleido.entity.movie.MovieDoubanWeeklyDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 豆瓣电影口碑榜Mapper接口
 *
 * @author cyetstar
 * @date 2023-12-22 11:18:26
 */
@Mapper
public interface MovieDoubanWeeklyMapper extends BaseMapper<MovieDoubanWeeklyDO> {

}