package cc.onelooker.kaleido.service.system.impl;

import cc.onelooker.kaleido.convert.system.SysDictConvert;
import cc.onelooker.kaleido.dto.system.SysDictDTO;
import cc.onelooker.kaleido.dto.system.req.SysDictCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysDictPageReq;
import cc.onelooker.kaleido.dto.system.req.SysDictUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysDictPageResp;
import cc.onelooker.kaleido.entity.system.SysDictDO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.mapper.system.SysDictMapper;
import cc.onelooker.kaleido.service.system.SysDictService;
import cc.onelooker.kaleido.third.plex.Directory;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.exception.ServiceException;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 字典表ServiceImpl
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 */
@Service
public class SysDictServiceImpl extends AbstractBaseServiceImpl<SysDictMapper, SysDictDO, SysDictDTO> implements SysDictService {

    SysDictConvert convert = SysDictConvert.INSTANCE;

    @Autowired
    private SysDictMapper sysDictMapper;

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

    @Override
    @Transactional
    public void deleteByDictType(String dictType) {
        Validate.notEmpty(dictType);
        SysDictDTO param = new SysDictDTO();
        param.setDictType(dictType);
        delete(param);
    }

    @Override
    public void deleteByDictType(List<String> dictTypeList) {

        LambdaQueryWrapper<SysDictDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SysDictDO::getDictType, dictTypeList);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public SysDictDTO findByDictTypeAndValue(String dictType, String value) {
        SysDictDTO sysDictDTO = sysDictMapper.findByDictTypeAndValue(dictType, value);
        return sysDictDTO;
    }

    @Override
    public SysDictDTO findByDictTypeAndLabel(String dictType, String label) {
        SysDictDTO sysDictDTO = sysDictMapper.findByDictTypeAndLabel(dictType, label);
        return sysDictDTO;
    }

    @Override
    public Boolean updateByDictType(String oldDictType, String newDictType) {
        if (StringUtils.equals(oldDictType, newDictType)) {
            return true;
        }

        LambdaUpdateWrapper<SysDictDO> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(SysDictDO::getDictType, oldDictType);

        SysDictDO sysDictDO = new SysDictDO();
        sysDictDO.setDictType(newDictType);
        return update(sysDictDO, updateWrapper);
    }

    @Override
    public Boolean create(SysDictCreateReq req) {
        SysDictDO sysDictDO = SysDictConvert.INSTANCE.convertToDO(req);
        checkDict(sysDictDO);
        return save(sysDictDO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean createBatch(List<SysDictCreateReq> reqs) {
        if (!CollectionUtils.isEmpty(reqs)) {
            for (SysDictCreateReq req : reqs) {
                create(req);
            }
        }
        return true;
    }

    @Override
    public Boolean updateById(SysDictUpdateReq req) {
        SysDictDO sysDictDO = SysDictConvert.INSTANCE.convertToDO(req);
        checkDict(sysDictDO);
        return updateById(sysDictDO);
    }

    @Override
    public List<SysDictDTO> listBySysDictType(String dictType) {
        return SysDictConvert.INSTANCE.convert(getList(dictType));
    }

    @Override
    public List<SysDictPageResp> listAll(SysDictPageReq req) {
        Wrapper<SysDictDO> queryWrapper = genQueryWrapper(SysDictConvert.INSTANCE.convertToDTO(req));
        return SysDictConvert.INSTANCE.convertToPageResp(list(queryWrapper));
    }

    @Override
    public void deleteByDictTypeAndValue(String dictType, String value) {
        Validate.notEmpty(dictType);
        Validate.notEmpty(value);
        SysDictDTO param = new SysDictDTO();
        param.setDictType(dictType);
        param.setValue(value);
        delete(param);
    }

    /**
     * 根据类型获取列表
     */
    private List<SysDictDO> getList(String dictType) {
        LambdaQueryWrapper<SysDictDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysDictDO::getDictType, dictType);
        queryWrapper.orderByAsc(SysDictDO::getSort, SysDictDO::getValue, SysDictDO::getId);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 校验字典
     */
    private void checkDict(SysDictDO dictDO) {
        if (exist(dictDO.getId(), dictDO.getDictType(), dictDO.getValue())) {
            throw new ServiceException(20001, "字典已存在");
        }
    }

    /**
     * 是否存在
     */
    private boolean exist(Long dictId, String dictType, String value) {
        LambdaQueryWrapper<SysDictDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(SysDictDO::getId);
        queryWrapper.eq(SysDictDO::getDictType, dictType);
        queryWrapper.eq(SysDictDO::getValue, value);
        queryWrapper.ne(Objects.nonNull(dictId), SysDictDO::getId, dictId);
        queryWrapper.last(" limit 1");
        return Objects.nonNull(baseMapper.selectOne(queryWrapper));
    }

}