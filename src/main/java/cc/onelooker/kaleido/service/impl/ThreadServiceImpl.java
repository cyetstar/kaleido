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
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        query.eq(StringUtils.isNotEmpty(dto.getBgmId()), ThreadDO::getBgmId, dto.getBgmId());
        query.eq(StringUtils.isNotEmpty(dto.getStatus()), ThreadDO::getStatus, dto.getStatus());
        query.like(StringUtils.isNotEmpty(dto.getTitle()), ThreadDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getUrl()), ThreadDO::getUrl, dto.getUrl());
        query.eq(StringUtils.isNotEmpty(dto.getWebsite()), ThreadDO::getWebsite, dto.getWebsite());
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

    @Override
    public List<ThreadDTO> listByDoubanId(String doubanId) {
        Validate.notEmpty(doubanId);
        ThreadDTO param = new ThreadDTO();
        param.setDoubanId(doubanId);
        return list(param);
    }

    @Override
    public List<ThreadDTO> listByImdbId(String imdbId) {
        Validate.notEmpty(imdbId);
        ThreadDTO param = new ThreadDTO();
        param.setImdbId(imdbId);
        return list(param);
    }

    @Override
    public List<ThreadDTO> listByBgmId(String bgmId) {
        Validate.notEmpty(bgmId);
        ThreadDTO param = new ThreadDTO();
        param.setBgmId(bgmId);
        return list(param);
    }

    @Override
    @Transactional
    public void updateStatus(String id, String status) {
        ThreadDO threadDO = new ThreadDO();
        threadDO.setId(id);
        threadDO.setStatus(status);
        updateById(threadDO);
    }
}