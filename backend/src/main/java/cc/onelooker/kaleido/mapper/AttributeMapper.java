package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.dto.AttributeDTO;
import cc.onelooker.kaleido.entity.AttributeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性Mapper接口
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 */
@Mapper
public interface AttributeMapper extends BaseMapper<AttributeDO> {

    List<AttributeDO> listBySubjectId(@Param("subjectId") String subjectId);
}