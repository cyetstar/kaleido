package cc.onelooker.kaleido.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjjcnt.common.core.dto.BaseDTO;
import com.zjjcnt.common.core.entity.IdEntity;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import com.zjjcnt.common.core.utils.ClassHelper;
import com.zjjcnt.common.file.FileInfo;
import com.zjjcnt.common.file.FileTransportManager;
import com.zjjcnt.common.file.enums.FileOperateOption;
import com.zjjcnt.common.util.DateTimeUtils;
import com.zjjcnt.common.util.reflect.ReflectionUtils;
import cc.onelooker.kaleido.convert.FileInfoConvert;
import cc.onelooker.kaleido.dto.FileInfoDTO;
import cc.onelooker.kaleido.dto.IFile;
import cc.onelooker.kaleido.dto.ISystem;
import cc.onelooker.kaleido.service.FileInfoService;
import cc.onelooker.kaleido.utils.CurrentUserUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author xiadawei
 * @Date 2022-05-31 00:35:00
 * @Description TODO
 */
public abstract class KaleidoBaseServiceImpl<M extends BaseMapper<T>, T extends IdEntity, D extends BaseDTO>
        extends AbstractBaseServiceImpl<M, T, D> {

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private FileTransportManager fileTransportManager;

    protected String bizTable = getBizTable();

    public String getBizTable() {
        String shortClassName = ClassUtils.getShortClassName(getEntityClass());
        shortClassName = StringUtils.removeEnd(shortClassName, ClassHelper.CLASS_SUFFIX_DO);
        return StringUtils.uncapitalize(shortClassName);
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
            throw new RuntimeException("存在重复");
        }
        if (dto instanceof ISystem) {
            ISystem systemDTO = (ISystem) dto;
            systemDTO.setCjrzh(CurrentUserUtils.getUsername());
            systemDTO.setCjr(CurrentUserUtils.getName());
            systemDTO.setCjsj(DateTimeUtils.now());
            systemDTO.setXgrzh(CurrentUserUtils.getUsername());
            systemDTO.setXgr(CurrentUserUtils.getName());
            systemDTO.setXgsj(DateTimeUtils.now());
        }
        try {
            ReflectionUtils.invokeSetter(dto, "glm", CurrentUserUtils.getDepartmentId());
        } catch (IllegalArgumentException e) {
        }
    }

    protected void beforeUpdate(D dto) {
        if (!checkUnique(dto)) {
            throw new RuntimeException("存在重复");
        }
        if (dto instanceof ISystem) {
            ISystem systemDTO = (ISystem) dto;
            systemDTO.setXgrzh(CurrentUserUtils.getUsername());
            systemDTO.setXgr(CurrentUserUtils.getName());
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
    }

    protected void saveMultipartFile(MultipartFile multipartFile, Serializable id, String bizType) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return;
        }
        FileInfo fileInfo = fileTransportManager.putObject(multipartFile, FileOperateOption.DEFAULT);
        FileInfoDTO fileInfoDTO = FileInfoConvert.INSTANCE.convert(fileInfo);
        fileInfoDTO.setBizId((Long) id);
        fileInfoDTO.setBizTable(bizTable);
        fileInfoDTO.setBizType(bizType);
        fileInfoService.insert(fileInfoDTO);
    }

    protected void deleteFile(IFile iFile) {
        List<FileInfoDTO> fileInfoDTOList = fileInfoService.listByBizIdAndBizTable((Long) iFile.getId(), getBizTable());
        for (FileInfoDTO fileInfoDTO : fileInfoDTOList) {
            fileInfoService.delete(fileInfoDTO);
        }
    }

}
