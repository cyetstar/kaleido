package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.service.SysDictTypeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cc.onelooker.kaleido.convert.SysDictTypeConvert;
import cc.onelooker.kaleido.dto.SysDictTypeDTO;
import cc.onelooker.kaleido.entity.SysDictTypeDO;
import cc.onelooker.kaleido.mapper.SysDictTypeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 字典表类型表ServiceImpl
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Service
public class SysDictTypeServiceImpl extends KaleidoBaseServiceImpl<SysDictTypeMapper, SysDictTypeDO, SysDictTypeDTO> implements SysDictTypeService {

    SysDictTypeConvert convert = SysDictTypeConvert.INSTANCE;

    @Override
    protected Wrapper<SysDictTypeDO> genQueryWrapper(SysDictTypeDTO sysDictTypeDTO) {
        LambdaQueryWrapper<SysDictTypeDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(sysDictTypeDTO.getType()), SysDictTypeDO::getType, sysDictTypeDTO.getType());
        query.eq(StringUtils.isNotEmpty(sysDictTypeDTO.getName()), SysDictTypeDO::getName, sysDictTypeDTO.getName());
        query.eq(Objects.nonNull(sysDictTypeDTO.getIsDeleted()), SysDictTypeDO::getIsDeleted, sysDictTypeDTO.getIsDeleted());
        return query;
    }

    @Override
    public SysDictTypeDTO convertToDTO(SysDictTypeDO sysDictTypeDO) {
        return convert.convert(sysDictTypeDO);
    }

    @Override
    public SysDictTypeDO convertToDO(SysDictTypeDTO sysDictTypeDTO) {
        return convert.convertToDO(sysDictTypeDTO);
    }

    @Override
    protected boolean checkUnique(SysDictTypeDTO dto) {
        SysDictTypeDTO param = new SysDictTypeDTO();
        param.setType(dto.getType());
        SysDictTypeDTO exist = find(param);
        return doCheckUnique(dto, exist);
    }
}