package cc.onelooker.kaleido.service.system.impl;

import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cc.onelooker.kaleido.convert.system.SysDeptConvert;
import cc.onelooker.kaleido.dto.system.SysDeptDTO;
import cc.onelooker.kaleido.entity.system.SysDeptDO;
import cc.onelooker.kaleido.mapper.system.SysDeptMapper;
import cc.onelooker.kaleido.service.system.SysDeptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 部门表ServiceImpl
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 */
@Service
public class SysDeptServiceImpl extends KaleidoBaseServiceImpl<SysDeptMapper, SysDeptDO, SysDeptDTO> implements SysDeptService {

    SysDeptConvert convert = SysDeptConvert.INSTANCE;

    @Override
    protected Wrapper<SysDeptDO> genQueryWrapper(SysDeptDTO sysDeptDTO) {
        LambdaQueryWrapper<SysDeptDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(sysDeptDTO.getId()), SysDeptDO::getId, sysDeptDTO.getId());
        query.eq(StringUtils.isNotEmpty(sysDeptDTO.getDeptCode()), SysDeptDO::getDeptCode, sysDeptDTO.getDeptCode());
        query.eq(StringUtils.isNotEmpty(sysDeptDTO.getParentCode()), SysDeptDO::getParentCode, sysDeptDTO.getParentCode());
        query.eq(Objects.nonNull(sysDeptDTO.getParentId()), SysDeptDO::getParentId, sysDeptDTO.getParentId());
        query.eq(StringUtils.isNotEmpty(sysDeptDTO.getDeptName()), SysDeptDO::getDeptName, sysDeptDTO.getDeptName());
        query.eq(StringUtils.isNotEmpty(sysDeptDTO.getLeader()), SysDeptDO::getLeader, sysDeptDTO.getLeader());
        query.eq(StringUtils.isNotEmpty(sysDeptDTO.getPhone()), SysDeptDO::getPhone, sysDeptDTO.getPhone());
        query.eq(StringUtils.isNotEmpty(sysDeptDTO.getEmail()), SysDeptDO::getEmail, sysDeptDTO.getEmail());
        query.eq(Objects.nonNull(sysDeptDTO.getOrderNum()), SysDeptDO::getOrderNum, sysDeptDTO.getOrderNum());
        query.eq(Objects.nonNull(sysDeptDTO.getSubCount()), SysDeptDO::getSubCount, sysDeptDTO.getSubCount());
        query.eq(Objects.nonNull(sysDeptDTO.getIsEnabled()), SysDeptDO::getIsEnabled, sysDeptDTO.getIsEnabled());
        query.eq(Objects.nonNull(sysDeptDTO.getIsDeleted()), SysDeptDO::getIsDeleted, sysDeptDTO.getIsDeleted());
        query.eq(Objects.nonNull(sysDeptDTO.getCreateTime()), SysDeptDO::getCreateTime, sysDeptDTO.getCreateTime());
        query.eq(Objects.nonNull(sysDeptDTO.getUpdateTime()), SysDeptDO::getUpdateTime, sysDeptDTO.getUpdateTime());
        query.eq(StringUtils.isNotEmpty(sysDeptDTO.getCreatedBy()), SysDeptDO::getCreatedBy, sysDeptDTO.getCreatedBy());
        query.eq(StringUtils.isNotEmpty(sysDeptDTO.getUpdatedBy()), SysDeptDO::getUpdatedBy, sysDeptDTO.getUpdatedBy());
        query.eq(StringUtils.isNotEmpty(sysDeptDTO.getAncestors()), SysDeptDO::getAncestors, sysDeptDTO.getAncestors());
        return query;
    }

    @Override
    public SysDeptDTO convertToDTO(SysDeptDO sysDeptDO) {
        return convert.convert(sysDeptDO);
    }

    @Override
    public SysDeptDO convertToDO(SysDeptDTO sysDeptDTO) {
        return convert.convertToDO(sysDeptDTO);
    }
}