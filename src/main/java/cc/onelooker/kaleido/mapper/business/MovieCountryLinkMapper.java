package cc.onelooker.kaleido.mapper.business;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.business.MovieCountryLinkDO;

import java.lang.Long;


/**
 * 电影国家地区关联表Mapper接口
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Mapper
public interface MovieCountryLinkMapper extends BaseMapper<MovieCountryLinkDO> {

}