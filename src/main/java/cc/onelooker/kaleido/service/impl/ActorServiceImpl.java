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
import cc.onelooker.kaleido.service.TvshowSeasonService;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
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

    @Autowired
    private TvshowSeasonService tvshowSeasonService;

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
        List<ActorDTO> actorDTOList = Lists.newArrayList();
        List<TvshowSeasonActorDTO> tvshowSeasonActorDTOList = tvshowSeasonActorService.listBySeasonId(seasonId);
        tvshowSeasonActorDTOList.stream().map(s -> {
            ActorDTO actorDTO = findById(s.getActorId());
            actorDTO.setRole(s.getRole());
            actorDTO.setPlayRole(s.getPlayRole());
            return actorDTO;
        });
        return actorDTOList;
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
            tvshowShowActorDTO.setActorId(s.getId());
            tvshowShowActorDTO.setRole(actorRole.name());
            tvshowShowActorDTO.setPlayRole(s.getPlayRole());
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
        actorDTOList.stream().forEach(s -> {
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

    private ActorDTO findOrSave(ActorDTO s) {
        ActorDTO actorDTO = null;
        if (StringUtils.isNotEmpty(s.getDoubanId())) {
            actorDTO = findByDoubanId(s.getDoubanId());
        }
        if (actorDTO == null && StringUtils.isNotEmpty(s.getThumb()) && !StringUtils.endsWith(s.getThumb(), KaleidoConstants.SUFFIX_PNG)) {
            actorDTO = findByThumb(s.getThumb());
        }
        if (actorDTO == null && StringUtils.isNotEmpty(s.getName())) {
            actorDTO = findByName(s.getName());
        }
        if (actorDTO == null) {
            actorDTO = new ActorDTO();
            actorDTO.setName(StringUtils.defaultString(s.getName(), s.getOriginalName()));
            actorDTO.setOriginalName(s.getOriginalName());
            actorDTO.setThumb(s.getThumb());
            actorDTO.setDoubanId(s.getDoubanId());
            actorDTO = insert(actorDTO);
        } else {
            actorDTO.setName(StringUtils.defaultString(s.getName(), s.getOriginalName()));
            actorDTO.setOriginalName(s.getOriginalName());
            actorDTO.setThumb(s.getThumb());
            actorDTO.setDoubanId(s.getDoubanId());
            update(actorDTO);
        }
        return actorDTO;
    }
}