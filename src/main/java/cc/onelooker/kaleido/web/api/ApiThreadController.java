package cc.onelooker.kaleido.web.api;

import cc.onelooker.kaleido.dto.ThreadDTO;
import cc.onelooker.kaleido.dto.req.ApiMovieThreadPageReq;
import cc.onelooker.kaleido.dto.req.ApiMovieThreadUpdateReq;
import cc.onelooker.kaleido.dto.req.ApiThreadViewReq;
import cc.onelooker.kaleido.dto.resp.ApiThreadUpdateResp;
import cc.onelooker.kaleido.dto.resp.ApiThreadViewResp;
import cc.onelooker.kaleido.service.ThreadService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author xiadawei
 * @Date 2024-01-10 15:13:00
 * @Description TODO
 */
@RestController
@RequestMapping("/api/thread")
public class ApiThreadController {

    @Autowired
    private ThreadService threadService;

    @PostMapping("view")
    public ApiThreadViewResp view(@RequestBody ApiThreadViewReq req) {
        ThreadDTO threadDTO = threadService.findById(req.getId());
        ApiThreadViewResp resp = new ApiThreadViewResp();
        resp.setId(req.getId());
        if (threadDTO != null) {
            resp.setStatus(String.valueOf(threadDTO.getStatus()));
        } else {
            resp.setStatus("none");
        }
        return resp;
    }

    @PostMapping("page")
    public Map<String, String> page(@RequestBody ApiMovieThreadPageReq req) {
        ThreadDTO param = new ThreadDTO();
        param.setIdList(Arrays.asList(req.getIds()));
        PageResult<ThreadDTO> pageResult = threadService.page(param, Page.of(1, 1000, false));
        return pageResult.getRecords().stream().collect(Collectors.toMap(ThreadDTO::getId, ThreadDTO::getStatus));
    }

    @PostMapping("update")
    public ApiThreadUpdateResp update(@RequestBody ApiMovieThreadUpdateReq req) {
        ThreadDTO threadDTO = threadService.findById(req.getId());
        if (threadDTO == null) {
            threadDTO = new ThreadDTO();
            threadDTO.setId(req.getId());
            threadDTO.setStatus(req.getStatus());
            threadService.insert(threadDTO);
        } else {
            threadDTO.setStatus(req.getStatus());
            threadService.update(threadDTO);
        }
        ApiThreadUpdateResp resp = new ApiThreadUpdateResp();
        resp.setId(req.getId());
        resp.setStatus(req.getStatus());
        return resp;
    }
}
