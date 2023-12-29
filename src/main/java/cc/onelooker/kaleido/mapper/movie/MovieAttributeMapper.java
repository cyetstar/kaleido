package cc.onelooker.kaleido.mapper.movie;

import cc.onelooker.kaleido.entity.movie.MovieAttributeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 电影属性值Mapper接口
 *
 * @author cyetstar
 * @date 2023-12-22 11:18:26
 */
@Mapper
public interface MovieAttributeMapper extends BaseMapper<MovieAttributeDO> {

}