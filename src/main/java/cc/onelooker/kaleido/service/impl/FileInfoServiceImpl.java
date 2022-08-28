package cc.onelooker.kaleido.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import com.zjjcnt.common.file.FileInfo;
import com.zjjcnt.common.file.FileTransportManager;
import com.zjjcnt.common.file.enums.FileOperateOption;
import cc.onelooker.kaleido.convert.FileInfoConvert;
import cc.onelooker.kaleido.dto.FileInfoDTO;
import cc.onelooker.kaleido.entity.FileInfoDO;
import cc.onelooker.kaleido.mapper.FileInfoMapper;
import cc.onelooker.kaleido.service.FileInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 文件信息ServiceImpl
 *
 * @author xiadawei
 * @date 2022-05-31 00:27:23
 */
@Service
public class FileInfoServiceImpl extends AbstractBaseServiceImpl<FileInfoMapper, FileInfoDO, FileInfoDTO> implements FileInfoService {

    FileInfoConvert convert = FileInfoConvert.INSTANCE;

    @Autowired
    private FileTransportManager fileTransportManager;

    @Override
    protected Wrapper<FileInfoDO> genQueryWrapper(FileInfoDTO fileInfoDTO) {
        LambdaQueryWrapper<FileInfoDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(fileInfoDTO.getFileId()), FileInfoDO::getFileId, fileInfoDTO.getFileId());
        query.eq(StringUtils.isNotEmpty(fileInfoDTO.getFilename()), FileInfoDO::getFilename, fileInfoDTO.getFilename());
        query.eq(StringUtils.isNotEmpty(fileInfoDTO.getContentType()), FileInfoDO::getContentType, fileInfoDTO.getContentType());
        query.eq(Objects.nonNull(fileInfoDTO.getBizId()), FileInfoDO::getBizId, fileInfoDTO.getBizId());
        query.eq(StringUtils.isNotEmpty(fileInfoDTO.getBizTable()), FileInfoDO::getBizTable, fileInfoDTO.getBizTable());
        query.eq(StringUtils.isNotEmpty(fileInfoDTO.getBizType()), FileInfoDO::getBizType, fileInfoDTO.getBizType());
        return query;
    }

    @Override
    public FileInfoDTO convertToDTO(FileInfoDO fileInfoDO) {
        return convert.convert(fileInfoDO);
    }

    @Override
    public FileInfoDO convertToDO(FileInfoDTO fileInfoDTO) {
        return convert.convertToDO(fileInfoDTO);
    }

    @Override
    public List<FileInfoDTO> listByBizIdAndBizTable(Long bizId, String bizTable) {
        Validate.notNull(bizId);
        Validate.notEmpty(bizTable);
        FileInfoDTO param = new FileInfoDTO();
        param.setBizId(bizId);
        param.setBizTable(bizTable);
        return list(param);
    }

    @Override
    public FileInfoDTO findByBizIdAndBizTable(Long bizId, String bizTable) {
        Validate.notNull(bizId);
        Validate.notEmpty(bizTable);
        FileInfoDTO param = new FileInfoDTO();
        param.setBizId(bizId);
        param.setBizTable(bizTable);
        return find(param);
    }

    @Override
    @Transactional
    public FileInfoDTO insert(FileInfoDTO dto) {
        if (dto.getFile() != null && !dto.getFile().isEmpty()) {
            FileInfo fileInfo = fileTransportManager.putObject(dto.getFile(), FileOperateOption.DEFAULT);
            dto.setFileId(fileInfo.getId());
            dto.setFilename(fileInfo.getFilename());
            dto.setContentType(fileInfo.getContentType());
            dto.setSize(fileInfo.getSize());
        }
        return super.insert(dto);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        FileInfoDTO fileInfoDTO = findById(id);
        return delete(fileInfoDTO);
    }

    @Override
    @Transactional
    public boolean delete(FileInfoDTO dto) {
        fileTransportManager.removeObject(dto.getFileId());
        return super.delete(dto);
    }
}