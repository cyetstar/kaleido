package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.VideoService;
import cc.onelooker.kaleido.entity.business.VideoDO;
import cc.onelooker.kaleido.dto.business.VideoDTO;
import cc.onelooker.kaleido.convert.business.VideoConvert;
import cc.onelooker.kaleido.mapper.business.VideoMapper;

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
public class VideoServiceImpl extends AbstractBaseServiceImpl<VideoMapper, VideoDO, VideoDTO> implements VideoService {

    VideoConvert convert = VideoConvert.INSTANCE;

    @Override
    protected Wrapper<VideoDO> genQueryWrapper(VideoDTO dto) {
        LambdaQueryWrapper<VideoDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getAspect()), VideoDO::getAspect, dto.getAspect());
        query.eq(Objects.nonNull(dto.getCodec()), VideoDO::getCodec, dto.getCodec());
        query.eq(Objects.nonNull(dto.getDurationinseconds()), VideoDO::getDurationinseconds, dto.getDurationinseconds());
        query.eq(Objects.nonNull(dto.getHeight()), VideoDO::getHeight, dto.getHeight());
        query.eq(Objects.nonNull(dto.getStereomode()), VideoDO::getStereomode, dto.getStereomode());
        query.eq(Objects.nonNull(dto.getWidth()), VideoDO::getWidth, dto.getWidth());
        query.eq(Objects.nonNull(dto.getFileinfoId()), VideoDO::getFileinfoId, dto.getFileinfoId());
        return query;
    }

    @Override
    public VideoDTO convertToDTO(VideoDO videoDO) {
        return convert.convert(videoDO);
    }

    @Override
    public VideoDO convertToDO(VideoDTO videoDTO) {
        return convert.convertToDO(videoDTO);
    }
}