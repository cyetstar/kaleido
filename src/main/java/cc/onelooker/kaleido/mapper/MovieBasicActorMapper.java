package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.MovieBasicActorDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 电影演职员关联表Mapper接口
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Mapper
public interface MovieBasicActorMapper extends BaseMapper<MovieBasicActorDO> {

}