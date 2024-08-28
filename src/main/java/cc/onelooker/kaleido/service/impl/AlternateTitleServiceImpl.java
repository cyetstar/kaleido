package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.AlternateTitleConvert;
import cc.onelooker.kaleido.dto.AlternateTitleDTO;
import cc.onelooker.kaleido.entity.AlternateTitleDO;
import cc.onelooker.kaleido.enums.SubjectType;
import cc.onelooker.kaleido.mapper.AlternateTitleMapper;
import cc.onelooker.kaleido.service.AlternateTitleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 别名ServiceImpl
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 */
@Service
public class AlternateTitleServiceImpl extends AbstractBaseServiceImpl<AlternateTitleMapper, AlternateTitleDO, AlternateTitleDTO> implements AlternateTitleService {

    AlternateTitleConvert convert = AlternateTitleConvert.INSTANCE;

    @Override
    protected Wrapper<AlternateTitleDO> genQueryWrapper(AlternateTitleDTO dto) {
        LambdaQueryWrapper<AlternateTitleDO> query = new LambdaQueryWrapper<>();
        query.like(StringUtils.isNotEmpty(dto.getTitle()), AlternateTitleDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getSubjectId()), AlternateTitleDO::getSubjectId, dto.getSubjectId());
        query.eq(StringUtils.isNotEmpty(dto.getSubjectType()), AlternateTitleDO::getSubjectType, dto.getSubjectType());
        return query;
    }

    @Override
    public AlternateTitleDTO convertToDTO(AlternateTitleDO alternateTitleDO) {
        return convert.convert(alternateTitleDO);
    }

    @Override
    public AlternateTitleDO convertToDO(AlternateTitleDTO alternateTitleDTO) {
        return convert.convertToDO(alternateTitleDTO);
    }

    @Override
    @Transactional
    public void deleteBySubjectId(String subjectId) {
        AlternateTitleDTO param = new AlternateTitleDTO();
        param.setSubjectId(subjectId);
        delete(param);
    }

    @Override
    public List<AlternateTitleDTO> listBySubjectId(String subjectId) {
        AlternateTitleDTO param = new AlternateTitleDTO();
        param.setSubjectId(subjectId);
        return list(param);
    }

    @Override
    public List<AlternateTitleDTO> listByTitleAndSubjectType(String title, SubjectType subjectType) {
        AlternateTitleDTO param = new AlternateTitleDTO();
        param.setTitle(title);
        param.setSubjectType(subjectType.name());
        return list(param);
    }

    @Override
    @Transactional
    public void updateTitles(List<String> alternateTitleList, String subjectId, SubjectType subjectType) {
        if (alternateTitleList == null) {
            return;
        }
        deleteBySubjectId(subjectId);
        alternateTitleList.forEach(s -> {
            AlternateTitleDTO alternateTitleDTO = new AlternateTitleDTO();
            alternateTitleDTO.setTitle(s);
            alternateTitleDTO.setSubjectId(subjectId);
            alternateTitleDTO.setSubjectType(subjectType.name());
            insert(alternateTitleDTO);
        });
    }
}