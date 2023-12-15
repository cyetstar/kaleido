package cc.onelooker.kaleido.support;

import cc.onelooker.kaleido.service.system.SysDictTypeService;
import cc.onelooker.kaleido.third.plex.Directory;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.dict.BaseDictionaryInitializer;
import cc.onelooker.kaleido.dto.system.SysDictDTO;
import cc.onelooker.kaleido.dto.system.SysDictTypeDTO;
import cc.onelooker.kaleido.service.IDictionaryService;
import cc.onelooker.kaleido.service.system.SysDictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private PlexApiService plexApiService;

    public static final String[] SECONDARY = {"genre", "country", "year", "decade", "contentRating",};

    @Override
    public Map<String, Map<String, String>> init() {
        Map<String, Map<String, String>> dicts = Maps.newLinkedHashMap();
        initSysDict(dicts);
        initPlexDict(dicts);
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

    private void initPlexDict(Map<String, Map<String, String>> dicts) {
        String libraryId = ConfigUtils.getSysConfig("plexMovieLibraryId");
        for (String secondary : SECONDARY) {
            List<Directory> directoryList = plexApiService.listDirectory(libraryId, secondary);
            TreeMap<String, String> dict = directoryList.stream().collect(Collectors.toMap(Directory::getKey, Directory::getTitle, (e1, e2) -> e1, TreeMap::new));
            dicts.put("movie" + StringUtils.capitalize(secondary), dict);
        }
    }

}
