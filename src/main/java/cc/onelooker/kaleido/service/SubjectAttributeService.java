package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.SubjectAttributeDTO;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 项目属性Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 */
public interface SubjectAttributeService extends IBaseService<SubjectAttributeDTO> {

    void deleteBySubjectIdAndAttributeType(String subject, String attributeType);

    void insert(String subjectId, String attributeId);

}