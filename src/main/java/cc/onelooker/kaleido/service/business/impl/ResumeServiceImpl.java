package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.ResumeService;
import cc.onelooker.kaleido.entity.business.ResumeDO;
import cc.onelooker.kaleido.dto.business.ResumeDTO;
import cc.onelooker.kaleido.convert.business.ResumeConvert;
import cc.onelooker.kaleido.mapper.business.ResumeMapper;

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
public class ResumeServiceImpl extends AbstractBaseServiceImpl<ResumeMapper, ResumeDO, ResumeDTO> implements ResumeService {

    ResumeConvert convert = ResumeConvert.INSTANCE;

    @Override
    protected Wrapper<ResumeDO> genQueryWrapper(ResumeDTO dto) {
        LambdaQueryWrapper<ResumeDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getPosition()), ResumeDO::getPosition, dto.getPosition());
        query.eq(Objects.nonNull(dto.getTotal()), ResumeDO::getTotal, dto.getTotal());
        query.eq(Objects.nonNull(dto.getMovieId()), ResumeDO::getMovieId, dto.getMovieId());
        return query;
    }

    @Override
    public ResumeDTO convertToDTO(ResumeDO resumeDO) {
        return convert.convert(resumeDO);
    }

    @Override
    public ResumeDO convertToDO(ResumeDTO resumeDTO) {
        return convert.convertToDO(resumeDTO);
    }
}