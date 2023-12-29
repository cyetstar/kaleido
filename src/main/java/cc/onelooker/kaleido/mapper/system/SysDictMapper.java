package cc.onelooker.kaleido.mapper.system;

import cc.onelooker.kaleido.dto.system.SysDictDTO;
import cc.onelooker.kaleido.entity.system.SysDictDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 字典表Mapper接口
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDictDO> {

    SysDictDTO findByDictTypeAndValue(@Param("dictType") String dictType, @Param("value") String value);

    SysDictDTO findByDictTypeAndLabel(@Param("dictType") String dictType, @Param("label") String label);
}