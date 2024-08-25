package cc.onelooker.kaleido.support;

import cc.onelooker.kaleido.dto.AttributeDTO;
import cc.onelooker.kaleido.dto.SysDictDTO;
import cc.onelooker.kaleido.dto.SysDictTypeDTO;
import cc.onelooker.kaleido.enums.AttributeType;
import cc.onelooker.kaleido.service.AttributeService;
import cc.onelooker.kaleido.service.IDictionaryService;
import cc.onelooker.kaleido.service.SysDictService;
import cc.onelooker.kaleido.service.SysDictTypeService;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.dict.BaseDictionaryInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 加载字典代码
 */
@Slf4j
@Component
public class DictInitializer extends BaseDictionaryInitializer {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private List<IDictionaryService> dictionaryServices;

    @Override
    public Map<String, Map<String, String>> init() {
        Map<String, Map<String, String>> dicts = Maps.newLinkedHashMap();
        initSysDict(dicts);
        initAttributeDict(dicts);
        for (IDictionaryService dictionaryService : dictionaryServices) {
            dicts.put(dictionaryService.getType(), dictionaryService.loadDict());
        }
        return dicts;
    }

    private void initAttributeDict(Map<String, Map<String, String>> dicts) {
        AttributeType[] types = new AttributeType[]{AttributeType.MovieCountry, AttributeType.MovieGenre, AttributeType.MovieLanguage};
        for (AttributeType type : types) {
            List<AttributeDTO> attributeDTOList = attributeService.listByType(type);
            for (AttributeDTO attributeDTO : attributeDTOList) {
                Map<String, String> dict = dicts.computeIfAbsent(attributeDTO.getType(), k -> Maps.newLinkedHashMap());
                dict.put(attributeDTO.getValue(), attributeDTO.getValue());
                dicts.put(attributeDTO.getType(), dict);
            }
        }
    }

    private void initSysDict(Map<String, Map<String, String>> dicts) {

        List<SysDictTypeDTO> sysDictTypeDTOList = sysDictTypeService.list(null);
        for (SysDictTypeDTO sysDictTypeDTO : sysDictTypeDTOList) {
            Map<String, String> dict = Maps.newLinkedHashMap();
            List<SysDictDTO> sysDictDTOList = sysDictService.listByDictType(sysDictTypeDTO.getType());
            Collections.sort(sysDictDTOList);
            for (SysDictDTO sysDictDTO : sysDictDTOList) {
                dict.put(sysDictDTO.getValue(), sysDictDTO.getLabel());
            }
            dicts.put(sysDictTypeDTO.getType(), dict);
        }
    }

}
