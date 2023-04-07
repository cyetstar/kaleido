package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.AudioService;
import cc.onelooker.kaleido.entity.business.AudioDO;
import cc.onelooker.kaleido.dto.business.AudioDTO;
import cc.onelooker.kaleido.convert.business.AudioConvert;
import cc.onelooker.kaleido.mapper.business.AudioMapper;

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
public class AudioServiceImpl extends AbstractBaseServiceImpl<AudioMapper, AudioDO, AudioDTO> implements AudioService {

    AudioConvert convert = AudioConvert.INSTANCE;

    @Override
    protected Wrapper<AudioDO> genQueryWrapper(AudioDTO dto) {
        LambdaQueryWrapper<AudioDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getChannels()), AudioDO::getChannels, dto.getChannels());
        query.eq(Objects.nonNull(dto.getCodec()), AudioDO::getCodec, dto.getCodec());
        query.eq(Objects.nonNull(dto.getLanguage()), AudioDO::getLanguage, dto.getLanguage());
        query.eq(Objects.nonNull(dto.getFileinfoId()), AudioDO::getFileinfoId, dto.getFileinfoId());
        return query;
    }

    @Override
    public AudioDTO convertToDTO(AudioDO audioDO) {
        return convert.convert(audioDO);
    }

    @Override
    public AudioDO convertToDO(AudioDTO audioDTO) {
        return convert.convertToDO(audioDTO);
    }
}