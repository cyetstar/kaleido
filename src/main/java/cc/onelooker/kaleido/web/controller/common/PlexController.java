package cc.onelooker.kaleido.web.controller.common;

import cc.onelooker.kaleido.dto.common.resp.PlexGetLibrariesResp;
import cc.onelooker.kaleido.plex.PlexApiService;
import cc.onelooker.kaleido.plex.resp.GetLibrariesResp;
import com.zjjcnt.common.core.domain.CommonResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xiadawei
 * @Date 2023-10-01 21:33:00
 * @Description TODO
 */
@RestController
@RequestMapping("/plex")
public class PlexController {

    @Autowired
    private PlexApiService plexApiService;

    @GetMapping("getLibraries")
    public CommonResult<List<PlexGetLibrariesResp>> getLibraries() {
        List<GetLibrariesResp.Directory> libraries = plexApiService.getLibraries();
        List<PlexGetLibrariesResp> result = libraries.stream().map(s -> {
            PlexGetLibrariesResp plexGetLibrariesResp = new PlexGetLibrariesResp();
            plexGetLibrariesResp.setKey(s.getKey());
            plexGetLibrariesResp.setName(s.getTitle());
            plexGetLibrariesResp.setType(s.getType());
            plexGetLibrariesResp.setPath(s.getLocation().getPath());
            return plexGetLibrariesResp;
        }).collect(Collectors.toList());
        return CommonResult.success(result);
    }
}
