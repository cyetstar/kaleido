package cc.onelooker.kaleido.web.controller.common;

import cc.onelooker.kaleido.dto.resp.KomgaListLibraryResp;
import cc.onelooker.kaleido.third.komga.KomgaApiService;
import cc.onelooker.kaleido.third.komga.Library;
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
@RequestMapping("/komga")
public class KomgaController {

    @Autowired
    private KomgaApiService komgaApiService;

    @PostMapping("listLibrary")
    public CommonResult<List<KomgaListLibraryResp>> listLibrary() {
        List<Library> libraries = komgaApiService.listLibrary();
        List<KomgaListLibraryResp> result = libraries.stream().map(s -> {
            KomgaListLibraryResp komgaListLibraryResp = new KomgaListLibraryResp();
            komgaListLibraryResp.setKey(s.getId());
            komgaListLibraryResp.setName(s.getName());
            komgaListLibraryResp.setPath(s.getRoot());
            return komgaListLibraryResp;
        }).collect(Collectors.toList());
        return CommonResult.success(result);
    }
}
