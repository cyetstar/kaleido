package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.AttributeDTO;
import cc.onelooker.kaleido.enums.AttributeType;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 属性Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 */
public interface AttributeService extends IBaseService<AttributeDTO> {

    AttributeDTO findByValueAndType(String value, AttributeType type);

    AttributeDTO insert(String value, AttributeType type);

    AttributeDTO insert(String id, String value, AttributeType type);

    List<AttributeDTO> listBySubjectId(String subjectId);

    List<AttributeDTO> listBySubjectIdList(List<String> subjectIdList);

    List<AttributeDTO> listByType(AttributeType type);

    void updateAttributes(List<String> attributeValueList, String subjectId, AttributeType type);

}