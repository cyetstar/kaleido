package cc.onelooker.kaleido.web.api;

import cc.onelooker.kaleido.dto.ComicBookDTO;
import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.dto.ThreadDTO;
import cc.onelooker.kaleido.dto.req.ApiThreadPageReq;
import cc.onelooker.kaleido.dto.req.ApiThreadUpdateReq;
import cc.onelooker.kaleido.dto.req.ApiThreadViewReq;
import cc.onelooker.kaleido.dto.resp.ApiThreadUpdateResp;
import cc.onelooker.kaleido.dto.resp.ApiThreadViewResp;
import cc.onelooker.kaleido.service.ComicBookService;
import cc.onelooker.kaleido.service.ComicSeriesService;
import cc.onelooker.kaleido.service.MovieBasicService;
import cc.onelooker.kaleido.service.ThreadService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private MovieBasicService movieBasicService;

    @Autowired
    private ComicSeriesService comicSeriesService;

    @Autowired
    private ComicBookService comicBookService;

    @PostMapping("view")
    public ApiThreadViewResp view(@RequestBody ApiThreadViewReq req) {
        ThreadDTO threadDTO = threadService.findById(req.getId());
        MovieBasicDTO movieBasicDTO = null;
        ComicSeriesDTO comicSeriesDTO = null;
        List<ThreadDTO> threadDTOList = Lists.newArrayList();
        if (StringUtils.isNotEmpty(req.getDoubanId())) {
            movieBasicDTO = movieBasicService.findByDoubanId(req.getDoubanId());
            threadDTOList.addAll(threadService.listByDoubanId(req.getDoubanId()));
        }
        if (movieBasicDTO == null && StringUtils.isNotEmpty(req.getImdbId())) {
            movieBasicDTO = movieBasicService.findByImdbId(req.getImdbId());
            threadDTOList.addAll(threadService.listByImdbId(req.getImdbId()));
        }
        if (StringUtils.isNotEmpty(req.getBgmId())) {
            comicSeriesDTO = comicSeriesService.findByBgmId(req.getBgmId());
            threadDTOList.addAll(threadService.listByBgmId(req.getBgmId()));
        }
        List<ApiThreadViewResp> threadList = threadDTOList.stream().filter(s -> !StringUtils.equals(s.getId(), req.getId())).map(s -> {
            ApiThreadViewResp resp = new ApiThreadViewResp();
            resp.setId(s.getId());
            resp.setTitle(s.getTitle());
            resp.setUrl(s.getUrl());
            resp.setStatus(s.getStatus());
            return resp;
        }).collect(Collectors.toList());

        ApiThreadViewResp resp = new ApiThreadViewResp();
        if (threadDTO != null) {
            resp.setId(threadDTO.getId());
            resp.setStatus(threadDTO.getStatus());
        } else {
            resp.setId(req.getId());
            resp.setStatus("none");
        }
        resp.setThreadList(threadList);
        if (movieBasicDTO != null) {
            resp.setMovieBasicId(movieBasicDTO.getId());
            resp.setFilenameList(Lists.newArrayList(movieBasicDTO.getFilename()));
        }
        if (comicSeriesDTO != null) {
            resp.setComicSeriesId(comicSeriesDTO.getId());
            List<ComicBookDTO> comicBookDTOList = comicBookService.listBySeriesId(comicSeriesDTO.getId());
            resp.setFilenameList(comicBookDTOList.stream().map(ComicBookDTO::getFilename).collect(Collectors.toList()));
        }
        return resp;
    }

    @PostMapping("page")
    public Map<String, String> page(@RequestBody ApiThreadPageReq req) {
        ThreadDTO param = new ThreadDTO();
        param.setIdList(Arrays.asList(req.getIds()));
        PageResult<ThreadDTO> pageResult = threadService.page(param, Page.of(1, 1000, false));
        return pageResult.getRecords().stream().collect(Collectors.toMap(ThreadDTO::getId, ThreadDTO::getStatus));
    }

    @PostMapping("update")
    public ApiThreadUpdateResp update(@RequestBody ApiThreadUpdateReq req) {
        ThreadDTO threadDTO = threadService.findById(req.getId());
        boolean isNew = false;
        if (threadDTO == null) {
            isNew = true;
            threadDTO = new ThreadDTO();
            threadDTO.setId(req.getId());
        }
        threadDTO.setTitle(req.getTitle());
        threadDTO.setUrl(req.getUrl());
        threadDTO.setDoubanId(req.getDoubanId());
        threadDTO.setImdbId(req.getImdbId());
        threadDTO.setBgmId(req.getBgmId());
        threadDTO.setWebsite(StringUtils.split(req.getId(), Constants.MINUS)[0]);
        threadDTO.setStatus(req.getStatus());
        if (isNew) {
            threadService.insert(threadDTO);
        } else {
            threadService.update(threadDTO);
        }
        ApiThreadUpdateResp resp = new ApiThreadUpdateResp();
        resp.setId(req.getId());
        resp.setStatus(req.getStatus());
        return resp;
    }
}
