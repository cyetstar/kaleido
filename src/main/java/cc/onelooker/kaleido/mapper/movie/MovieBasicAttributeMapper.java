package cc.onelooker.kaleido.mapper.movie;

import cc.onelooker.kaleido.entity.movie.MovieBasicAttributeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 电影属性值关联表Mapper接口
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */
@Mapper
public interface MovieBasicAttributeMapper extends BaseMapper<MovieBasicAttributeDO> {

}