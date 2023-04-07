package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.FilenameService;
import cc.onelooker.kaleido.entity.business.FilenameDO;
import cc.onelooker.kaleido.dto.business.FilenameDTO;
import cc.onelooker.kaleido.convert.business.FilenameConvert;
import cc.onelooker.kaleido.mapper.business.FilenameMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.String;

/**
 * ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 */
@Service
public class FilenameServiceImpl extends AbstractBaseServiceImpl<FilenameMapper, FilenameDO, FilenameDTO> implements FilenameService {

    FilenameConvert convert = FilenameConvert.INSTANCE;

    @Override
    protected Wrapper<FilenameDO> genQueryWrapper(FilenameDTO dto) {
        LambdaQueryWrapper<FilenameDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getValue()), FilenameDO::getValue, dto.getValue());
        query.eq(Objects.nonNull(dto.getThreadId()), FilenameDO::getThreadId, dto.getThreadId());
        return query;
    }

    @Override
    public FilenameDTO convertToDTO(FilenameDO filenameDO) {
        return convert.convert(filenameDO);
    }

    @Override
    public FilenameDO convertToDO(FilenameDTO filenameDTO) {
        return convert.convertToDO(filenameDTO);
    }
}