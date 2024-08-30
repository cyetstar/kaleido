package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.AuthorConvert;
import cc.onelooker.kaleido.dto.AuthorDTO;
import cc.onelooker.kaleido.dto.ComicSeriesAuthorDTO;
import cc.onelooker.kaleido.entity.AuthorDO;
import cc.onelooker.kaleido.enums.AuthorRole;
import cc.onelooker.kaleido.mapper.AuthorMapper;
import cc.onelooker.kaleido.service.AuthorService;
import cc.onelooker.kaleido.service.ComicSeriesAuthorService;
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
 * 漫画作者ServiceImpl
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Service
public class AuthorServiceImpl extends AbstractBaseServiceImpl<AuthorMapper, AuthorDO, AuthorDTO> implements AuthorService {

    AuthorConvert convert = AuthorConvert.INSTANCE;

    @Autowired
    private ComicSeriesAuthorService comicSeriesAuthorService;

    @Override
    protected Wrapper<AuthorDO> genQueryWrapper(AuthorDTO dto) {
        LambdaQueryWrapper<AuthorDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getName()), AuthorDO::getName, dto.getName());
        query.in(CollectionUtils.isNotEmpty(dto.getIdList()), AuthorDO::getId, dto.getIdList());
        query.like(StringUtils.isNotEmpty(dto.getKeyword()), AuthorDO::getName, dto.getKeyword());
        return query;
    }

    @Override
    public AuthorDTO convertToDTO(AuthorDO authorDO) {
        return convert.convert(authorDO);
    }

    @Override
    public AuthorDO convertToDO(AuthorDTO authorDTO) {
        return convert.convertToDO(authorDTO);
    }

    @Override
    public AuthorDTO findByName(String name) {
        Validate.notEmpty(name);
        AuthorDTO param = new AuthorDTO();
        param.setName(name);
        return find(param);
    }

    @Override
    public AuthorDTO insert(String name) {
        AuthorDTO dto = new AuthorDTO();
        dto.setName(name);
        return insert(dto);
    }

    @Override
    public List<AuthorDTO> listByKeyword(String keyword) {
        AuthorDTO param = new AuthorDTO();
        param.setKeyword(keyword);
        return list(param);
    }

    @Override
    public List<AuthorDTO> listBySeriesId(String seriesId) {
        List<ComicSeriesAuthorDTO> comicSeriesAuthorDTOList = comicSeriesAuthorService.listBySeriesId(seriesId);
        return comicSeriesAuthorDTOList.stream().map(s -> {
            AuthorDTO authorDTO = findById(s.getAuthorId());
            authorDTO.setRole(s.getRole());
            return authorDTO;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateAuthors(List<AuthorDTO> authorDTOList, String seriesId, AuthorRole authorRole) {
        if (authorDTOList == null) {
            return;
        }
        baseMapper.deleteBySeriesIdAndRole(seriesId, authorRole.name());
        authorDTOList.stream().forEach(s -> {
            ComicSeriesAuthorDTO comicSeriesAuthorDTO = new ComicSeriesAuthorDTO();
            comicSeriesAuthorDTO.setSeriesId(seriesId);
            comicSeriesAuthorDTO.setAuthorId(s.getId());
            comicSeriesAuthorDTO.setRole(authorRole.name());
            comicSeriesAuthorService.insert(comicSeriesAuthorDTO);
        });
    }
}