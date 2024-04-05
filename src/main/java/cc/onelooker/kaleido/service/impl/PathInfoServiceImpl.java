package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.PathInfoConvert;
import cc.onelooker.kaleido.dto.PathInfoDTO;
import cc.onelooker.kaleido.entity.PathInfoDO;
import cc.onelooker.kaleido.mapper.PathInfoMapper;
import cc.onelooker.kaleido.service.PathInfoService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 目录信息ServiceImpl
 *
 * @author cyetstar
 * @date 2024-03-23 23:08:41
 */
@Service
public class PathInfoServiceImpl extends AbstractBaseServiceImpl<PathInfoMapper, PathInfoDO, PathInfoDTO> implements PathInfoService {

    PathInfoConvert convert = PathInfoConvert.INSTANCE;

    @Override
    protected Wrapper<PathInfoDO> genQueryWrapper(PathInfoDTO dto) {
        LambdaQueryWrapper<PathInfoDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getPath()), PathInfoDO::getPath, dto.getPath());
        query.eq(StringUtils.isNotEmpty(dto.getImdbId()), PathInfoDO::getImdbId, dto.getImdbId());
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), PathInfoDO::getDoubanId, dto.getDoubanId());
        query.eq(StringUtils.isNotEmpty(dto.getTmdbId()), PathInfoDO::getTmdbId, dto.getTmdbId());
        query.eq(StringUtils.isNotEmpty(dto.getBgmId()), PathInfoDO::getBgmId, dto.getBgmId());
        return query;
    }

    @Override
    public PathInfoDTO convertToDTO(PathInfoDO pathInfoDO) {
        return convert.convert(pathInfoDO);
    }

    @Override
    public PathInfoDO convertToDO(PathInfoDTO pathInfoDTO) {
        return convert.convertToDO(pathInfoDTO);
    }

    @Override
    @Transactional
    public void insert(String path, String bgmId) {
        PathInfoDTO pathInfoDTO = new PathInfoDTO();
        pathInfoDTO.setPath(path);
        pathInfoDTO.setBgmId(bgmId);
        insert(pathInfoDTO);
    }

    @Override
    public PathInfoDTO findByPath(String path) {
        PathInfoDTO param = new PathInfoDTO();
        param.setPath(path);
        return find(param);
    }

    @Override
    public void deleteByPath(String path) {
        PathInfoDTO param = new PathInfoDTO();
        param.setPath(path);
        delete(param);
    }
}