package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieBasicConvert;
import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicSyncPlexReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicViewResp;
import cc.onelooker.kaleido.exp.movie.MovieBasicExp;
import cc.onelooker.kaleido.plex.PlexApiService;
import cc.onelooker.kaleido.plex.resp.GetMovies;
import cc.onelooker.kaleido.service.movie.MovieBasicService;
import cc.onelooker.kaleido.service.movie.MovieCountryService;
import cc.onelooker.kaleido.service.movie.MovieGenreService;
import cc.onelooker.kaleido.service.movie.MovieManager;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.ExportColumn;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.exception.ServiceException;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;

/**
 * 电影前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */

@Slf4j
@Api(tags = "电影")
@RestController
@RequestMapping("/movieBasic")
public class MovieBasicController extends AbstractCrudController<MovieBasicDTO> {

    @Autowired
    private MovieBasicService movieBasicService;

    @Autowired
    private MovieCountryService movieCountryService;

    @Autowired
    private MovieGenreService movieGenreService;

    @Autowired
    private MovieManager movieManager;

    @Autowired
    private PlexApiService plexApiService;

    @Override
    protected IBaseService getService() {
        return movieBasicService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影")
    public CommonResult<PageResult<MovieBasicPageResp>> page(MovieBasicPageReq req, PageParam pageParam) {
        pageParam.setOrderBy("DESC:added_at");
        return super.page(req, pageParam, MovieBasicConvert.INSTANCE::convertToDTO, MovieBasicConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影详情")
    public CommonResult<MovieBasicViewResp> view(Long id) {
        MovieBasicViewResp resp = super.doView(id, MovieBasicConvert.INSTANCE::convertToViewResp);
        return CommonResult.success(resp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影")
    public CommonResult<MovieBasicCreateResp> create(@RequestBody MovieBasicCreateReq req) {
        return super.create(req, MovieBasicConvert.INSTANCE::convertToDTO, MovieBasicConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影")
    public CommonResult<Boolean> update(@RequestBody MovieBasicUpdateReq req) {
        return super.update(req, MovieBasicConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieBasicExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影")
    public void export(MovieBasicPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieBasicExp.class, MovieBasicConvert.INSTANCE::convertToDTO, MovieBasicConvert.INSTANCE::convertToExp, response);
    }

    @PostMapping("syncPlex")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> syncPlex() {
        String libraryId = ConfigUtils.getSysConfig("plexMovieLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需同步资料库信息");
        }
        //获取最后修改时间
        Long maxUpdatedAt = movieBasicService.findMaxUpdatedAt();
        String libraryPath = plexApiService.getLibraryPath(libraryId);
        List<GetMovies.Metadata> metadataList = maxUpdatedAt == null ? plexApiService.listMovie(libraryId) : plexApiService.listMovieByUpdatedAt(libraryId, maxUpdatedAt);
        metadataList.sort(Comparator.comparing(GetMovies.Metadata::getUpdatedAt, Comparator.nullsLast(Comparator.naturalOrder())));
        for (GetMovies.Metadata metadata : metadataList) {
            try {
                movieManager.syncPlexMovie(libraryPath, metadata);
            } catch (Exception e) {
                log.error("同步资料库失败，错误信息：", e);
            }
        }
        return CommonResult.success(true);
    }

    @PostMapping("syncPlexById")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> syncPlexById(@RequestBody MovieBasicSyncPlexReq req) {
        String libraryId = ConfigUtils.getSysConfig("plexMovieLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需要同步资料库信息");
        }
        String libraryPath = plexApiService.getLibraryPath(libraryId);
        movieManager.syncPlexMovieById(libraryPath, req.getId());
        return CommonResult.success(true);
    }

}