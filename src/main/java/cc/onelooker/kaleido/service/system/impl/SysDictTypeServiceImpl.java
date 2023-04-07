package cc.onelooker.kaleido.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zjjcnt.common.core.dict.Dictionary;
import com.zjjcnt.common.core.exception.ServiceException;
import cc.onelooker.kaleido.convert.system.SysDictTypeConvert;
import cc.onelooker.kaleido.dto.system.SysDictTypeDTO;
import cc.onelooker.kaleido.dto.system.req.SysDictTypeUpdateReq;
import cc.onelooker.kaleido.entity.system.SysDictTypeDO;
import cc.onelooker.kaleido.mapper.system.SysDictTypeMapper;
import cc.onelooker.kaleido.service.ExBaseServiceImpl;
import cc.onelooker.kaleido.service.system.SysDictService;
import cc.onelooker.kaleido.service.system.SysDictTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 字典表类型表ServiceImpl
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Service
public class SysDictTypeServiceImpl extends ExBaseServiceImpl<SysDictTypeMapper, SysDictTypeDO, SysDictTypeDTO> implements SysDictTypeService {

    SysDictTypeConvert convert = SysDictTypeConvert.INSTANCE;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private ApplicationContext applicationContext;

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

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        SysDictTypeDTO sysDictTypeDTO = findById(id);
        sysDictService.deleteByDictType(sysDictTypeDTO.getType());
        return super.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateById(SysDictTypeUpdateReq req) {
        SysDictTypeDTO sysDictTypeDTO = SysDictTypeConvert.INSTANCE.convertToDTO(req);
        if (!checkUnique(sysDictTypeDTO)) {
            throw new ServiceException(20001, "存在重复(" + getBizTable() + ")");
        }

        SysDictTypeDO oldSysDictTypeDO = getById(req.getId());
        sysDictService.updateByDictType(oldSysDictTypeDO.getType(), sysDictTypeDTO.getType());

        return updateById(SysDictTypeConvert.INSTANCE.convertToDO(sysDictTypeDTO));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByIds(Long[] ids) {
        if (ids != null) {
            List<Long> idList = Arrays.asList(ids);

            List<SysDictTypeDO> dictTypeList = getDictTypeList(idList);
            baseMapper.deleteBatchIds(idList);

            if (!CollectionUtils.isEmpty(dictTypeList)) {
                sysDictService.deleteByDictType(dictTypeList.stream().map(SysDictTypeDO::getType).collect(Collectors.toList()));
            }
        }

        return Boolean.TRUE;
    }

    @Override
    public Boolean reload() {
        Dictionary dictionary = new Dictionary(applicationContext);
        dictionary.loadAll();
        return true;
    }

    private List<SysDictTypeDO> getDictTypeList(List<Long> idList) {
        LambdaQueryWrapper<SysDictTypeDO> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.in(SysDictTypeDO::getId, idList);

        return list(queryWrapper);
    }
}