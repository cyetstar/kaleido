package cc.onelooker.kaleido.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.dict.Dictionary;
import com.zjjcnt.common.core.entity.IdEntity;
import cc.onelooker.kaleido.dto.IDictionary;
import cc.onelooker.kaleido.service.IDictionaryService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author xiadawei
 * @Date 2022-05-31 00:35:00
 * @Description TODO
 */
public abstract class DictionaryBaseServiceImpl<M extends BaseMapper<T>, T extends IdEntity, D extends IDictionary>
        extends KaleidoBaseServiceImpl<M, T, D> implements IDictionaryService {

    @Override
    @Transactional
    public D insert(D dto) {
        dto = super.insert(dto);
        Dictionary.put(bizTable, String.valueOf(dto.getKey()), dto.getLabel());
        return dto;
    }

    @Override
    @Transactional
    public boolean update(D dto) {
        boolean result = super.update(dto);
        Dictionary.put(bizTable, String.valueOf(dto.getKey()), dto.getLabel());
        return result;
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        D dto = super.findById(id);
        Dictionary.remove(bizTable, String.valueOf(dto.getKey()));
        return super.deleteById(id);
    }

    @Override
    @Transactional
    public boolean delete(D dto) {
        List<D> dtos = list(dto);
        dtos.forEach(s -> {
            Dictionary.remove(bizTable, String.valueOf(s.getKey()));
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
            dict.put(String.valueOf(d.getKey()), d.getLabel());
        }
        return dict;
    }
}
