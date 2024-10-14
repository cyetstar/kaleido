package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.ActorConvert;
import cc.onelooker.kaleido.dto.ActorDTO;
import cc.onelooker.kaleido.dto.MovieBasicActorDTO;
import cc.onelooker.kaleido.dto.TvshowSeasonActorDTO;
import cc.onelooker.kaleido.entity.ActorDO;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.mapper.ActorMapper;
import cc.onelooker.kaleido.service.ActorService;
import cc.onelooker.kaleido.service.MovieBasicActorService;
import cc.onelooker.kaleido.service.TvshowSeasonActorService;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 演职员ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class ActorServiceImpl extends AbstractBaseServiceImpl<ActorMapper, ActorDO, ActorDTO> implements ActorService {

    ActorConvert convert = ActorConvert.INSTANCE;

    @Autowired
    private MovieBasicActorService movieBasicActorService;

    @Autowired
    private TvshowSeasonActorService tvshowSeasonActorService;

    @Override
    protected Wrapper<ActorDO> genQueryWrapper(ActorDTO dto) {
        LambdaQueryWrapper<ActorDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), ActorDO::getDoubanId, dto.getDoubanId());
        query.eq(StringUtils.isNotEmpty(dto.getName()), ActorDO::getName, dto.getName());
        query.like(StringUtils.isNotEmpty(dto.getThumb()), ActorDO::getThumb, dto.getThumb());
        query.eq(StringUtils.isNotEmpty(dto.getOriginalName()), ActorDO::getOriginalName, dto.getOriginalName());
        query.like(StringUtils.isNotEmpty(dto.getKeyword()), ActorDO::getName, dto.getKeyword());
        query.in(CollectionUtils.isNotEmpty(dto.getIdList()), ActorDO::getId, dto.getIdList());
        return query;
    }

    @Override
    public ActorDTO convertToDTO(ActorDO actorDO) {
        return convert.convert(actorDO);
    }

    @Override
    public ActorDO convertToDO(ActorDTO actorDTO) {
        return convert.convertToDO(actorDTO);
    }

    @Override
    @Transactional
    public ActorDTO insert(ActorDTO dto) {
        ActorDTO actorDTO = null;
        if (StringUtils.isNotEmpty(dto.getDoubanId())) {
            actorDTO = findByDoubanId(dto.getDoubanId());
        } else {
            actorDTO = findByName(dto.getName());
            if (actorDTO != null && StringUtils.equals(dto.getDisambiguation(), actorDTO.getDisambiguation())) {
                throw new RuntimeException("该演员已存在");
            }
        }
        if (actorDTO != null) {
            throw new RuntimeException("该演员已存在");
        }
        return super.insert(dto);
    }

    @Override
    public List<ActorDTO> listByMovieId(String movieId) {
        List<MovieBasicActorDTO> movieBasicActorDTOList = movieBasicActorService.listByMovieId(movieId);
        return movieBasicActorDTOList.stream().map(s -> {
            ActorDTO actorDTO = findById(s.getActorId());
            actorDTO.setRole(s.getRole());
            actorDTO.setPlayRole(s.getPlayRole());
            return actorDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ActorDTO> listBySeasonId(String seasonId) {
        List<TvshowSeasonActorDTO> tvshowSeasonActorDTOList = tvshowSeasonActorService.listBySeasonId(seasonId);
        return tvshowSeasonActorDTOList.stream().map(s -> {
            ActorDTO actorDTO = findById(s.getActorId());
            actorDTO.setRole(s.getRole());
            actorDTO.setPlayRole(s.getPlayRole());
            return actorDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public ActorDTO findByName(String name) {
        Validate.notEmpty(name);
        ActorDTO param = new ActorDTO();
        param.setName(name);
        return find(param);
    }

    @Override
    public ActorDTO findByThumb(String thumb) {
        Validate.notEmpty(thumb);
        ActorDTO param = new ActorDTO();
        param.setThumb(thumb);
        return find(param);
    }

    @Override
    public ActorDTO findByDoubanId(String doubanId) {
        Validate.notEmpty(doubanId);
        ActorDTO param = new ActorDTO();
        param.setDoubanId(doubanId);
        return find(param);
    }

    @Override
    public void updateSeasonActors(List<ActorDTO> actorDTOList, String seasonId, ActorRole actorRole) {
        if (actorDTOList == null) {
            return;
        }
        baseMapper.deleteBySeasonIdAndRole(seasonId, actorRole.name());
        actorDTOList.stream().forEach(s -> {
            TvshowSeasonActorDTO tvshowShowActorDTO = new TvshowSeasonActorDTO();
            tvshowShowActorDTO.setSeasonId(seasonId);
            tvshowShowActorDTO.setRole(actorRole.name());
            tvshowShowActorDTO.setPlayRole(s.getPlayRole());
            if (StringUtils.isEmpty(s.getId())) {
                ActorDTO actorDTO = findOrSave(s);
                tvshowShowActorDTO.setActorId(actorDTO.getId());
            } else {
                tvshowShowActorDTO.setActorId(s.getId());
            }
            tvshowSeasonActorService.insert(tvshowShowActorDTO);
        });
    }

    @Override
    @Transactional
    public void updateMovieActors(List<ActorDTO> actorDTOList, String movieId, ActorRole actorRole) {
        if (actorDTOList == null) {
            return;
        }
        baseMapper.deleteByMovieIdAndRole(movieId, actorRole.name());
        actorDTOList.forEach(s -> {
            MovieBasicActorDTO movieBasicActorDTO = new MovieBasicActorDTO();
            movieBasicActorDTO.setMovieId(movieId);
            movieBasicActorDTO.setRole(actorRole.name());
            movieBasicActorDTO.setPlayRole(s.getPlayRole());
            if (StringUtils.isEmpty(s.getId())) {
                ActorDTO actorDTO = findOrSave(s);
                movieBasicActorDTO.setActorId(actorDTO.getId());
            } else {
                movieBasicActorDTO.setActorId(s.getId());
            }
            movieBasicActorService.insert(movieBasicActorDTO);
        });
    }

    private ActorDTO findOrSave(ActorDTO dto) {
        ActorDTO actorDTO = null;
        if (StringUtils.isNotEmpty(dto.getDoubanId())) {
            actorDTO = findByDoubanId(dto.getDoubanId());
        }
        if (actorDTO == null && StringUtils.isNotEmpty(dto.getThumb()) && !StringUtils.endsWith(dto.getThumb(), KaleidoConstants.SUFFIX_PNG)) {
            actorDTO = findByThumb(dto.getThumb());
        }
        if (actorDTO == null && StringUtils.isNotEmpty(dto.getName())) {
            actorDTO = findByName(dto.getName());
        }
        if (actorDTO == null) {
            actorDTO = new ActorDTO();
            actorDTO.setName(StringUtils.defaultString(dto.getName(), dto.getOriginalName()));
            actorDTO.setOriginalName(dto.getOriginalName());
            actorDTO.setThumb(dto.getThumb());
            actorDTO.setDoubanId(dto.getDoubanId());
            actorDTO = insert(actorDTO);
        } else {
            actorDTO.setName(StringUtils.defaultString(dto.getName(), dto.getOriginalName()));
            actorDTO.setOriginalName(dto.getOriginalName());
            actorDTO.setThumb(StringUtils.defaultString(dto.getThumb(), actorDTO.getThumb()));
            actorDTO.setDoubanId(StringUtils.defaultString(dto.getDoubanId(), actorDTO.getDoubanId()));
            update(actorDTO);
        }
        return actorDTO;
    }
}