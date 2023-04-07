package cc.onelooker.kaleido.mapper.business;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.business.RatingDO;

import java.lang.Long;
import java.lang.Double;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;


/**
 * Mapper接口
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 */
@Mapper
public interface RatingMapper extends BaseMapper<RatingDO> {

}