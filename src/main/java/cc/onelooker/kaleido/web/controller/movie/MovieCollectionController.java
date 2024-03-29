package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieCollectionConvert;
import cc.onelooker.kaleido.dto.movie.MovieBasicCollectionDTO;
import cc.onelooker.kaleido.dto.movie.MovieCollectionDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieCollectionCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieCollectionPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieCollectionSyncReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieCollectionListByMovieIdResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieCollectionPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieCollectionViewResp;
import cc.onelooker.kaleido.service.movie.MovieBasicCollectionService;
import cc.onelooker.kaleido.service.movie.MovieCollectionService;
import cc.onelooker.kaleido.service.movie.MovieManager;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 电影集合前端控制器
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */

@Api(tags = "电影集合")
@RestController
@RequestMapping("/movieCollection")
public class MovieCollectionController extends AbstractCrudController<MovieCollectionDTO> {

    @Autowired
    private MovieCollectionService movieCollectionService;

    @Autowired
    private MovieBasicCollectionService movieBasicCollectionService;

    @Autowired
    private MovieManager movieManager;

    @Override
    protected IBaseService getService() {
        return movieCollectionService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影集合")
    public CommonResult<PageResult<MovieCollectionPageResp>> page(MovieCollectionPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieCollectionConvert.INSTANCE::convertToDTO, MovieCollectionConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影集合详情")
    public CommonResult<MovieCollectionViewResp> view(Long id) {
        return super.view(id, MovieCollectionConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影集合")
    public CommonResult<Boolean> create(@RequestBody MovieCollectionCreateReq req) {
        movieManager.createCollection(req.getDoubanId());
        return CommonResult.success(true);
    }

    @PostMapping("sync")
    @ApiOperation(value = "同步电影集合")
    public CommonResult<Boolean> sync(@RequestBody MovieCollectionSyncReq req) {
        movieManager.syncCollection(req.getId());
        return CommonResult.success(true);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影集合")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping("listByMovieId")
    public CommonResult<List<MovieCollectionListByMovieIdResp>> listByMovieId(Long movieId) {
        List<MovieCollectionListByMovieIdResp> respList = Lists.newArrayList();
        List<MovieBasicCollectionDTO> movieBasicCollectionDTOList = movieBasicCollectionService.listMovieId(movieId);
        List<Long> collectionIdList = movieBasicCollectionDTOList.stream().map(MovieBasicCollectionDTO::getCollectionId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(collectionIdList)) {
            MovieCollectionDTO param = new MovieCollectionDTO();
            param.setIdList(collectionIdList);
            List<MovieCollectionDTO> movieCollectionDTOList = movieCollectionService.list(param);
            for (MovieCollectionDTO movieCollectionDTO : movieCollectionDTOList) {
                MovieCollectionListByMovieIdResp resp = MovieCollectionConvert.INSTANCE.convertToListByMovieIdResp(movieCollectionDTO);
                respList.add(resp);
            }
        }
        return CommonResult.success(respList);
    }

}