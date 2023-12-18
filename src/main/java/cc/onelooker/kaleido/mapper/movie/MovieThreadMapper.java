package cc.onelooker.kaleido.mapper.movie;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.movie.MovieThreadDO;

/**
 * 电影发布记录Mapper接口
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
@Mapper
public interface MovieThreadMapper extends BaseMapper<MovieThreadDO> {

}