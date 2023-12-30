package cc.onelooker.kaleido.service.system.impl;

import cc.onelooker.kaleido.convert.system.SysDictTypeConvert;
import cc.onelooker.kaleido.dto.system.SysDictDTO;
import cc.onelooker.kaleido.dto.system.SysDictTypeDTO;
import cc.onelooker.kaleido.dto.system.req.SysDictTypeUpdateReq;
import cc.onelooker.kaleido.entity.system.SysDictTypeDO;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.mapper.system.SysDictTypeMapper;
import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import cc.onelooker.kaleido.service.system.SysDictService;
import cc.onelooker.kaleido.service.system.SysDictTypeService;
import cc.onelooker.kaleido.third.plex.Directory;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zjjcnt.common.core.dict.Dictionary;
import com.zjjcnt.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典表类型表ServiceImpl
 *
 * @author cyetstar
 * @date 2022-04-26 00:41:00
 */
@Slf4j
@Service
public class SysDictTypeServiceImpl extends KaleidoBaseServiceImpl<SysDictTypeMapper, SysDictTypeDO, SysDictTypeDTO> implements SysDictTypeService {

    SysDictTypeConvert convert = SysDictTypeConvert.INSTANCE;

    public static final String[] IGNORE_SECONDARY = {"id", "firstCharacter", "actor", "director", "collection"};

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PlexApiService plexApiService;

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

    @Override
    public SysDictTypeDTO findByType(String type) {
        SysDictTypeDTO param = new SysDictTypeDTO();
        param.setType(type);
        return find(param);
    }

    @Override
    public void syncPlex() {
        String plexMovieLibraryId = ConfigUtils.getSysConfig(ConfigKey.plexMovieLibraryId);
        String plexMusicLibraryId = ConfigUtils.getSysConfig(ConfigKey.plexMusicLibraryId);
        String plexTvshowLibraryId = ConfigUtils.getSysConfig(ConfigKey.plexTvshowLibraryId);
        if (StringUtils.isNotEmpty(plexMovieLibraryId)) {
            syncDirectory(plexMovieLibraryId, "movie");
        }
        if (StringUtils.isNotEmpty(plexMusicLibraryId)) {
            syncDirectory(plexMusicLibraryId, "music");
        }
        if (StringUtils.isNotEmpty(plexTvshowLibraryId)) {
            syncDirectory(plexTvshowLibraryId, "tvshow");
        }
    }

    private void syncDirectory(String librayId, String prefix) {
        List<Directory> secondaryList = plexApiService.listSecondary(librayId);
        String type = null;
        for (Directory secondary : secondaryList) {
            try {
                if (BooleanUtils.isNotTrue(secondary.getSecondary()) || ArrayUtils.contains(IGNORE_SECONDARY, secondary.getKey())) {
                    continue;
                }
                type = prefix + StringUtils.capitalize(secondary.getKey());
                SysDictTypeDTO sysDictTypeDTO = findByType(type);
                if (sysDictTypeDTO == null) {
                    sysDictTypeDTO = new SysDictTypeDTO();
                    sysDictTypeDTO.setType(type);
                    sysDictTypeDTO.setIsDeleted(false);
                    sysDictTypeDTO.setCreateTime(new Date());
                    sysDictTypeDTO.setUpdateTime(new Date());
                    insert(sysDictTypeDTO);
                }
                List<Directory> directoryList = plexApiService.listDirectoryBySecondary(librayId, secondary.getKey());
                List<SysDictDTO> sysDictDTOList = sysDictService.listByDictType(type);
                List<String> keyList = directoryList.stream().map(Directory::getKey).collect(Collectors.toList());
                List<String> valueList = sysDictDTOList.stream().map(SysDictDTO::getValue).collect(Collectors.toList());
                Collection<String> addList = CollectionUtils.subtract(keyList, valueList);
                Collection<String> deleteList = CollectionUtils.subtract(valueList, keyList);
                for (Directory directory : directoryList) {
                    if (addList.contains(directory.getKey())) {
                        SysDictDTO sysDictDTO = new SysDictDTO();
                        sysDictDTO.setDictType(type);
                        sysDictDTO.setValue(directory.getKey());
                        sysDictDTO.setLabel(directory.getTitle());
                        sysDictDTO.setIsDeleted(false);
                        sysDictDTO.setCreateTime(new Date());
                        sysDictDTO.setUpdateTime(new Date());
                        sysDictService.insert(sysDictDTO);
                    }
                }
                for (String value : deleteList) {
                    sysDictService.deleteByDictTypeAndValue(type, value);
                }

            } catch (Exception e) {
                log.error("{}同步发生错误：{}", type, ExceptionUtil.getMessage(e));
            }
        }
    }

    private List<SysDictTypeDO> getDictTypeList(List<Long> idList) {
        LambdaQueryWrapper<SysDictTypeDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SysDictTypeDO::getId, idList);
        return list(queryWrapper);
    }
}