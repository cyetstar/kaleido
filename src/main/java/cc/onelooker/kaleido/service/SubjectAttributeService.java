package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.SubjectAttributeDTO;
import cc.onelooker.kaleido.enums.AttributeType;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 项目属性Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 */
public interface SubjectAttributeService extends IBaseService<SubjectAttributeDTO> {

    void deleteBySubjectIdAndAttributeType(String subject, AttributeType type);

    void insert(String subjectId, String attributeId);

    List<SubjectAttributeDTO> listBySubjectIdAndAttributeType(String subjectId, AttributeType type);
}