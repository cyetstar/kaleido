package cc.onelooker.kaleido.mapper.business;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cc.onelooker.kaleido.entity.business.MovieLanguageDO;

import java.lang.Long;
import java.lang.String;


/**
 * 语言Mapper接口
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Mapper
public interface MovieLanguageMapper extends BaseMapper<MovieLanguageDO> {

}