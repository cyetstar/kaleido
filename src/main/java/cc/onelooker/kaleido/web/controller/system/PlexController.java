package cc.onelooker.kaleido.web.controller.system;

import cc.onelooker.kaleido.dto.system.req.PlexGetLibrariesReq;
import cc.onelooker.kaleido.dto.system.resp.PlexGetLibrariesResp;
import cc.onelooker.kaleido.third.plex.Directory;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.zjjcnt.common.core.domain.CommonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("getLibraries")
    public CommonResult<List<PlexGetLibrariesResp>> getLibraries(@RequestBody PlexGetLibrariesReq req) {
        if (StringUtils.isEmpty(ConfigUtils.getSysConfig("plexUrl"))
                || StringUtils.isEmpty(ConfigUtils.getSysConfig("plexToken"))) {
            plexApiService.init(req.getPlexUrl(), req.getPlexToken());
        }
        List<Directory> libraries = plexApiService.listLibrary();
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
