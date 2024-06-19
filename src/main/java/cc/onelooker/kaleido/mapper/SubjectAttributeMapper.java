package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.SubjectAttributeDO;
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
public interface SubjectAttributeMapper extends BaseMapper<SubjectAttributeDO> {

    void deleteBySubjectIdAndAttributeType(@Param("subjectId") String subjectId, @Param("attributeType") String attributeType);

    List<SubjectAttributeDO> listBySubjectIdAndAttributeType(@Param("subjectId") String subjectId, @Param("attributeType") String attributeType);
}