package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.ThreadConvert;
import cc.onelooker.kaleido.dto.ThreadDTO;
import cc.onelooker.kaleido.entity.ThreadDO;
import cc.onelooker.kaleido.mapper.ThreadMapper;
import cc.onelooker.kaleido.service.ThreadService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 发布记录ServiceImpl
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
@Service
public class ThreadServiceImpl extends AbstractBaseServiceImpl<ThreadMapper, ThreadDO, ThreadDTO> implements ThreadService {

    ThreadConvert convert = ThreadConvert.INSTANCE;

    @Override
    protected Wrapper<ThreadDO> genQueryWrapper(ThreadDTO dto) {
        LambdaQueryWrapper<ThreadDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), ThreadDO::getDoubanId, dto.getDoubanId());
        query.eq(StringUtils.isNotEmpty(dto.getImdbId()), ThreadDO::getImdbId, dto.getImdbId());
        query.eq(StringUtils.isNotEmpty(dto.getStatus()), ThreadDO::getStatus, dto.getStatus());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), ThreadDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getUrl()), ThreadDO::getUrl, dto.getUrl());
        query.in(CollectionUtils.isNotEmpty(dto.getIdList()), ThreadDO::getId, dto.getIdList());
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