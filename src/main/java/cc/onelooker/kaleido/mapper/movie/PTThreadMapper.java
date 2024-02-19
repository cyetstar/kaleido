package cc.onelooker.kaleido.mapper.movie;

import cc.onelooker.kaleido.entity.movie.MovieThreadDO;
import cc.onelooker.kaleido.entity.movie.PTThreadDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 电影发布记录Mapper接口
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
@Mapper
public interface PTThreadMapper extends BaseMapper<PTThreadDO> {

}