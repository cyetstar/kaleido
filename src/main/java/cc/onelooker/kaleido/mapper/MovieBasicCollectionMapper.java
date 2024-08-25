package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.MovieBasicCollectionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 电影集合关联表Mapper接口
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */
@Mapper
public interface MovieBasicCollectionMapper extends BaseMapper<MovieBasicCollectionDO> {

}