package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.AlternateTitleDTO;
import cc.onelooker.kaleido.enums.SubjectType;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 别名Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 */
public interface AlternateTitleService extends IBaseService<AlternateTitleDTO> {

    void deleteBySubjectId(String subjectId);

    List<AlternateTitleDTO> listBySubjectId(String subjectId);

    List<AlternateTitleDTO> listByTitleAndSubjectType(String title, SubjectType subjectType);

    void updateTitles(List<String> alternateTitleList, String subjectId, SubjectType subjectType);

}