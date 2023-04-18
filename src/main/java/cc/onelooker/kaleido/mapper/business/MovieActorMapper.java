package cc.onelooker.kaleido.mapper.business;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.business.MovieActorDO;

import java.lang.Long;
import com.zjjcnt.common.core.annotation.Crypto;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;


/**
 * 演职员Mapper接口
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Mapper
public interface MovieActorMapper extends BaseMapper<MovieActorDO> {

}