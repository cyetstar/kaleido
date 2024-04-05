package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.convert.movie.PTThreadConvert;
import cc.onelooker.kaleido.dto.movie.PTThreadDTO;
import cc.onelooker.kaleido.entity.movie.PTThreadDO;
import cc.onelooker.kaleido.mapper.movie.PTThreadMapper;
import cc.onelooker.kaleido.service.movie.PTThreadService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 电影发布记录ServiceImpl
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
@Service
public class PTThreadServiceImpl extends AbstractBaseServiceImpl<PTThreadMapper, PTThreadDO, PTThreadDTO> implements PTThreadService {

    PTThreadConvert convert = PTThreadConvert.INSTANCE;

    @Override
    protected Wrapper<PTThreadDO> genQueryWrapper(PTThreadDTO dto) {
        LambdaQueryWrapper<PTThreadDO> query = new LambdaQueryWrapper<>();
        query.like(StringUtils.isNotEmpty(dto.getTitle()), PTThreadDO::getTitle, dto.getTitle());
        return query;
    }

    @Override
    public PTThreadDTO convertToDTO(PTThreadDO ptThreadDO) {
        return convert.convert(ptThreadDO);
    }

    @Override
    public PTThreadDO convertToDO(PTThreadDTO ptThreadDTO) {
        return convert.convertToDO(ptThreadDTO);
    }

}