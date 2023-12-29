package cc.onelooker.kaleido.support;

import cc.onelooker.kaleido.dto.system.SysDictDTO;
import cc.onelooker.kaleido.dto.system.SysDictTypeDTO;
import cc.onelooker.kaleido.service.IDictionaryService;
import cc.onelooker.kaleido.service.system.SysDictService;
import cc.onelooker.kaleido.service.system.SysDictTypeService;
import cc.onelooker.kaleido.third.plex.Directory;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.dict.BaseDictionaryInitializer;
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

    public static final String[] INCLUDE = {"genre", "country", "year", "decade", "contentRating", "rating"};

    @Override
    public Map<String, Map<String, String>> init() {
        Map<String, Map<String, String>> dicts = Maps.newLinkedHashMap();
        initSysDict(dicts);
//        initPlexDict(dicts);
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
        String movieLibraryId = ConfigUtils.getSysConfig("plexMovieLibraryId");
        String tvshowLibraryId = ConfigUtils.getSysConfig("plexTvshowLibraryId");
        String musicLibraryId = ConfigUtils.getSysConfig("plexMusicLibraryId");
        for (String libraryId : Arrays.asList(movieLibraryId, tvshowLibraryId, musicLibraryId)) {
            List<Directory> directoryList = plexApiService.listSecondary(libraryId);
            for (Directory directory : directoryList) {
                if (!StringUtils.equalsAny(directory.getKey(), INCLUDE)) {
                    continue;
                }
                List<Directory> directories = plexApiService.listDirectoryBySecondary(libraryId, directory.getKey());
                if (directories == null) {
                    continue;
                }
                TreeMap<String, String> dict = directories.stream().collect(Collectors.toMap(Directory::getKey, Directory::getTitle, (e1, e2) -> e1, TreeMap::new));
                dicts.put(libraryId + directory.getKey(), dict);
            }
        }
    }

}
