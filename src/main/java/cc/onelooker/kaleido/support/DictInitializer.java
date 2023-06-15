package cc.onelooker.kaleido.support;

import cc.onelooker.kaleido.service.system.SysDictTypeService;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.dict.BaseDictionaryInitializer;
import cc.onelooker.kaleido.dto.system.SysDictDTO;
import cc.onelooker.kaleido.dto.system.SysDictTypeDTO;
import cc.onelooker.kaleido.service.IDictionaryService;
import cc.onelooker.kaleido.service.system.SysDictService;
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
    private List<IDictionaryService> dictionaryServices;

    @Override
    public Map<String, Map<String, String>> init() {
        Map<String, Map<String, String>> dicts = Maps.newLinkedHashMap();
        initSysDict(dicts);
        for (IDictionaryService dictionaryService : dictionaryServices) {
            dicts.put(dictionaryService.getType(), dictionaryService.loadDict());
        }
        return dicts;
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
