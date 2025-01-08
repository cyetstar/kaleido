package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.IDictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.dict.Dictionary;
import com.zjjcnt.common.core.entity.IdEntity;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author cyetstar
 * @Date 2022-05-31 00:35:00
 * @Description TODO
 */
public abstract class DictionaryBaseServiceImpl<ID extends Serializable, M extends BaseMapper<T>, T extends IdEntity<ID>, D extends IDictionary<ID>>
        extends KaleidoBaseServiceImpl<ID, M, T, D> implements IDictionaryService {

    @Override
    @Transactional
    public D insert(D dto) {
        dto = super.insert(dto);
        Dictionary.put(bizTable, String.valueOf(dto.getId()), dto.getTitle());
        return dto;
    }

    @Override
    @Transactional
    public boolean update(D dto) {
        boolean result = super.update(dto);
        Dictionary.put(bizTable, String.valueOf(dto.getId()), dto.getTitle());
        return result;
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        D dto = super.findById(id);
        Dictionary.remove(bizTable, String.valueOf(dto.getId()));
        return super.deleteById(id);
    }

    @Override
    @Transactional
    public boolean delete(D dto) {
        List<D> dtos = list(dto);
        dtos.forEach(s -> {
            Dictionary.remove(bizTable, String.valueOf(s.getId()));
        });
        return super.delete(dto);
    }

    @Override
    public String getType() {
        return getBizTable();
    }

    @Override
    public Map<String, String> loadDict() {
        List<D> list = list((D) null);
        Map<String, String> dict = Maps.newLinkedHashMap();
        for (D d : list) {
            dict.put(String.valueOf(d.getId()), d.getTitle());
        }
        return dict;
    }
}
