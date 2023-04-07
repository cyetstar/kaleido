package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.ThreadService;
import cc.onelooker.kaleido.entity.business.ThreadDO;
import cc.onelooker.kaleido.dto.business.ThreadDTO;
import cc.onelooker.kaleido.convert.business.ThreadConvert;
import cc.onelooker.kaleido.mapper.business.ThreadMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Boolean;

/**
 * ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 */
@Service
public class ThreadServiceImpl extends AbstractBaseServiceImpl<ThreadMapper, ThreadDO, ThreadDTO> implements ThreadService {

    ThreadConvert convert = ThreadConvert.INSTANCE;

    @Override
    protected Wrapper<ThreadDO> genQueryWrapper(ThreadDTO dto) {
        LambdaQueryWrapper<ThreadDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getCreatedAt()), ThreadDO::getCreatedAt, dto.getCreatedAt());
        query.eq(Objects.nonNull(dto.getDoubanId()), ThreadDO::getDoubanId, dto.getDoubanId());
        query.eq(Objects.nonNull(dto.getImdb()), ThreadDO::getImdb, dto.getImdb());
        query.eq(Objects.nonNull(dto.getLinks()), ThreadDO::getLinks, dto.getLinks());
        query.eq(Objects.nonNull(dto.getPublishDate()), ThreadDO::getPublishDate, dto.getPublishDate());
        query.eq(Objects.nonNull(dto.getRating()), ThreadDO::getRating, dto.getRating());
        query.eq(Objects.nonNull(dto.getStatus()), ThreadDO::getStatus, dto.getStatus());
        query.eq(Objects.nonNull(dto.getThanks()), ThreadDO::getThanks, dto.getThanks());
        query.eq(Objects.nonNull(dto.getTitle()), ThreadDO::getTitle, dto.getTitle());
        query.eq(Objects.nonNull(dto.getType()), ThreadDO::getType, dto.getType());
        query.eq(Objects.nonNull(dto.getUpdatedAt()), ThreadDO::getUpdatedAt, dto.getUpdatedAt());
        query.eq(Objects.nonNull(dto.getUrl()), ThreadDO::getUrl, dto.getUrl());
        return query;
    }

    @Override
    public ThreadDTO convertToDTO(ThreadDO threadDO) {
        return convert.convert(threadDO);
    }

    @Override
    public ThreadDO convertToDO(ThreadDTO threadDTO) {
        return convert.convertToDO(threadDTO);
    }
}