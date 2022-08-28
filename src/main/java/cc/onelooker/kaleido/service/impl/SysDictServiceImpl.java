package cc.onelooker.kaleido.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.convert.SysDictConvert;
import cc.onelooker.kaleido.dto.SysDictDTO;
import cc.onelooker.kaleido.entity.SysDictDO;
import cc.onelooker.kaleido.mapper.SysDictMapper;
import cc.onelooker.kaleido.service.SysDictService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 字典表ServiceImpl
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Service
public class SysDictServiceImpl extends AbstractBaseServiceImpl<SysDictMapper, SysDictDO, SysDictDTO> implements SysDictService {

    SysDictConvert convert = SysDictConvert.INSTANCE;

    @Override
    protected Wrapper<SysDictDO> genQueryWrapper(SysDictDTO sysDictDTO) {
        LambdaQueryWrapper<SysDictDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(sysDictDTO.getId()), SysDictDO::getId, sysDictDTO.getId());
        query.eq(StringUtils.isNotEmpty(sysDictDTO.getDictType()), SysDictDO::getDictType, sysDictDTO.getDictType());
        query.eq(StringUtils.isNotEmpty(sysDictDTO.getLabel()), SysDictDO::getLabel, sysDictDTO.getLabel());
        query.eq(StringUtils.isNotEmpty(sysDictDTO.getValue()), SysDictDO::getValue, sysDictDTO.getValue());
        query.eq(Objects.nonNull(sysDictDTO.getSort()), SysDictDO::getSort, sysDictDTO.getSort());
        query.eq(Objects.nonNull(sysDictDTO.getIsEnabled()), SysDictDO::getIsEnabled, sysDictDTO.getIsEnabled());
        query.eq(Objects.nonNull(sysDictDTO.getIsDeleted()), SysDictDO::getIsDeleted, sysDictDTO.getIsDeleted());
        query.eq(Objects.nonNull(sysDictDTO.getCreateTime()), SysDictDO::getCreateTime, sysDictDTO.getCreateTime());
        query.eq(Objects.nonNull(sysDictDTO.getUpdateTime()), SysDictDO::getUpdateTime, sysDictDTO.getUpdateTime());
        query.eq(StringUtils.isNotEmpty(sysDictDTO.getCreatedBy()), SysDictDO::getCreatedBy, sysDictDTO.getCreatedBy());
        query.eq(StringUtils.isNotEmpty(sysDictDTO.getUpdatedBy()), SysDictDO::getUpdatedBy, sysDictDTO.getUpdatedBy());
        return query;
    }

    @Override
    public SysDictDTO convertToDTO(SysDictDO sysDictDO) {
        return convert.convert(sysDictDO);
    }

    @Override
    public SysDictDO convertToDO(SysDictDTO sysDictDTO) {
        return convert.convertToDO(sysDictDTO);
    }

    @Override
    public List<SysDictDTO> listByDictType(String dictType) {
        Validate.notEmpty(dictType);
        SysDictDTO param = new SysDictDTO();
        param.setDictType(dictType);
        return list(param);
    }
}