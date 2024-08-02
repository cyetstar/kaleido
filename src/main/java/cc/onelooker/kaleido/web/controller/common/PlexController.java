package cc.onelooker.kaleido.web.controller.common;

import cc.onelooker.kaleido.dto.system.resp.PlexListLibraryResp;
import cc.onelooker.kaleido.third.plex.Directory;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import com.zjjcnt.common.core.domain.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author cyetstar
 * @Date 2023-10-01 21:33:00
 * @Description TODO
 */
@RestController
@RequestMapping("/plex")
public class PlexController {

    @Autowired
    private PlexApiService plexApiService;

    @PostMapping("listLibrary")
    public CommonResult<List<PlexListLibraryResp>> listLibrary() {
        List<Directory> libraries = plexApiService.listLibrary();
        List<PlexListLibraryResp> result = libraries.stream().map(s -> {
            PlexListLibraryResp plexGetLibrariesResp = new PlexListLibraryResp();
            plexGetLibrariesResp.setKey(s.getKey());
            plexGetLibrariesResp.setName(s.getTitle());
            plexGetLibrariesResp.setType(s.getType());
            plexGetLibrariesResp.setPath(s.getLocation().getPath());
            return plexGetLibrariesResp;
        }).collect(Collectors.toList());
        return CommonResult.success(result);
    }
}
