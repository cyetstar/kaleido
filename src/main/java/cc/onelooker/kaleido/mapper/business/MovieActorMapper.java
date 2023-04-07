package cc.onelooker.kaleido.mapper.business;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.business.MovieActorDO;

import java.lang.Long;


/**
 * Mapper接口
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 */
@Mapper
public interface MovieActorMapper extends BaseMapper<MovieActorDO> {

}