package cc.onelooker.kaleido.mapper.business;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.business.MovieUniqueidDO;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.Dict;


/**
 * 电影唯一标识Mapper接口
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Mapper
public interface MovieUniqueidMapper extends BaseMapper<MovieUniqueidDO> {

}