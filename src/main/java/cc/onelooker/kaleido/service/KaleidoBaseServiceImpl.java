package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.convert.system.FileInfoConvert;
import cc.onelooker.kaleido.dto.IFile;
import cc.onelooker.kaleido.dto.ISystem;
import cc.onelooker.kaleido.dto.system.FileInfoDTO;
import cc.onelooker.kaleido.service.system.FileInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.dto.BaseDTO;
import com.zjjcnt.common.core.entity.IdEntity;
import com.zjjcnt.common.core.exception.ServiceException;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import com.zjjcnt.common.core.utils.ClassHelper;
import com.zjjcnt.common.file.FileInfo;
import com.zjjcnt.common.file.FileTransportManager;
import com.zjjcnt.common.file.enums.FileOperateOption;
import com.zjjcnt.common.util.DateTimeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author cyetstar
 * @Date 2022-05-31 00:35:00
 * @Description TODO
 */
public abstract class KaleidoBaseServiceImpl<M extends BaseMapper<T>, T extends IdEntity, D extends BaseDTO> extends AbstractBaseServiceImpl<M, T, D> {

    @Autowired
    protected FileInfoService fileInfoService;

    @Autowired
    protected FileTransportManager fileTransportManager;

    protected String bizTable = getBizTable();

    public String getBizTable() {
        String shortClassName = ClassUtils.getShortClassName(getEntityClass());
        shortClassName = StringUtils.removeEnd(shortClassName, ClassHelper.CLASS_SUFFIX_DO);
        return StringUtils.uncapitalize(shortClassName);
    }

    @Override
    public PageResult<D> page(@Nullable D dto, Page page) {
        LambdaQueryWrapper<T> queryWrapper = null;
        if (dto != null) {
            queryWrapper = (LambdaQueryWrapper) genQueryWrapper(dto);
        }
        page = getBaseMapper().selectPage(page, queryWrapper);
        return convertToDTO(page);
    }

    @Override
    public List<D> list(@Nullable D dto) {
        LambdaQueryWrapper<T> queryWrapper = null;
        if (dto != null) {
            queryWrapper = (LambdaQueryWrapper) genQueryWrapper(dto);
        }
        List<T> list = getBaseMapper().selectList(queryWrapper);
        return convertToDTO(list);
    }

    @Override
    @Transactional
    public D insert(D dto) {
        beforeInsert(dto);
        dto = super.insert(dto);
        if (dto instanceof IFile) {
            saveFile((IFile) dto);
        }
        return dto;
    }

    @Override
    @Transactional
    public boolean update(D dto) {
        beforeUpdate(dto);
        if (dto instanceof IFile) {
            saveFile((IFile) dto);
        }
        boolean result = super.update(dto);
        return result;
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        D dto = super.findById(id);
        if (dto instanceof IFile) {
            deleteFile((IFile) dto);
        }
        return super.deleteById(id);
    }

    protected void beforeInsert(D dto) {
        if (!checkUnique(dto)) {
            throw new ServiceException(20001, "存在重复(" + getBizTable() + ")");
        }
        if (dto instanceof ISystem) {
            ISystem systemDTO = (ISystem) dto;
            systemDTO.setCjsj(DateTimeUtils.now());
            systemDTO.setXgsj(DateTimeUtils.now());
        }

    }

    protected void beforeUpdate(D dto) {
        if (!checkUnique(dto)) {
            throw new ServiceException(20001, "存在重复(" + getBizTable() + ")");
        }
        if (dto instanceof ISystem) {
            ISystem systemDTO = (ISystem) dto;
            systemDTO.setXgsj(DateTimeUtils.now());
        }
    }

    protected boolean checkUnique(D dto) {
        return true;
    }

    protected boolean doCheckUnique(D newDTO, D existDTO) {
        if (existDTO == null) {
            return true;
        }
        if (newDTO.getId() != null) {
            return newDTO.getId().equals(existDTO.getId());
        } else {
            return false;
        }
    }

    protected void saveFile(IFile iFile) {
        Map<String, List<MultipartFile>> files = iFile.getFiles();
        for (String key : files.keySet()) {
            List<MultipartFile> multipartFiles = files.get(key);
            if (CollectionUtils.isEmpty(multipartFiles)) {
                continue;
            }
            for (MultipartFile multipartFile : multipartFiles) {
                saveMultipartFile(multipartFile, iFile.getId(), key);
            }
        }
        List<String> fileIds = iFile.getFileIds();
        if (fileIds != null) {
            for (String fileId : fileIds) {
                fileInfoService.bindBizInfo(fileId, iFile.getId().toString(), getBizTable());
            }
        }
    }

    protected void saveMultipartFile(MultipartFile multipartFile, Serializable id, String bizType) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return;
        }
        FileInfo fileInfo = fileTransportManager.putObject(multipartFile, FileOperateOption.DEFAULT);
        FileInfoDTO fileInfoDTO = FileInfoConvert.INSTANCE.convert(fileInfo);
        fileInfoDTO.setBizId(String.valueOf(id));
        fileInfoDTO.setBizTable(bizTable);
        fileInfoDTO.setBizType(bizType);
        fileInfoService.insert(fileInfoDTO);
    }

    protected void deleteFile(IFile iFile) {
        List<FileInfoDTO> fileInfoDTOList = fileInfoService.listByBizIdAndBizTable(String.valueOf(iFile.getId()), getBizTable());
        for (FileInfoDTO fileInfoDTO : fileInfoDTOList) {
            fileInfoService.delete(fileInfoDTO);
        }
    }

}
