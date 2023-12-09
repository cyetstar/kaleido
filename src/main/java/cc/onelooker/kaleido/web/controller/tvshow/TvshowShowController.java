package cc.onelooker.kaleido.web.controller.tvshow;

import cc.onelooker.kaleido.convert.movie.MovieBasicConvert;
import cc.onelooker.kaleido.convert.tvshow.TvshowShowConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowActorDTO;
import cc.onelooker.kaleido.dto.tvshow.TvshowGenreDTO;
import cc.onelooker.kaleido.dto.tvshow.TvshowShowDTO;
import cc.onelooker.kaleido.dto.tvshow.TvshowShowGenreDTO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowPageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowPageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowViewResp;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.exp.tvshow.TvshowShowExp;
import cc.onelooker.kaleido.service.tvshow.*;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.ExportColumn;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.function.ConvertFunction;
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
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 剧集前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */

@Slf4j
@Api(tags = "剧集")
@RestController
@RequestMapping("/tvshowShow")
public class TvshowShowController extends AbstractCrudController<TvshowShowDTO> {

    @Autowired
    private TvshowShowService tvshowShowService;

    @Autowired
    private TvshowActorService tvshowActorService;

    @Autowired
    private TvshowShowGenreService tvshowShowGenreService;

    @Autowired
    private TvshowManager tvshowManager;

    @Autowired
    private PlexApiService plexApiService;

    @Override
    protected IBaseService getService() {
        return tvshowShowService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询剧集")
    public CommonResult<PageResult<TvshowShowPageResp>> page(TvshowShowPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TvshowShowConvert.INSTANCE::convertToDTO, TvshowShowConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看剧集详情")
    public CommonResult<TvshowShowViewResp> view(Long id) {
        TvshowShowViewResp resp = doView(id, TvshowShowConvert.INSTANCE::convertToViewResp);
        List<TvshowActorDTO> tvshowActorDTOList = tvshowActorService.listByShowId(id);
        List<TvshowShowGenreDTO> tvshowShowGenreDTOList = tvshowShowGenreService.listByShowId(id);
        resp.setActorList(tvshowActorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Actor.name())).map(TvshowShowConvert.INSTANCE::convertToViewResp).collect(Collectors.toList()));
        resp.setGenreList(tvshowShowGenreDTOList.stream().map(s -> new TvshowShowViewResp.Genre(String.valueOf(s.getGenreId()))).collect(Collectors.toList()));
        return CommonResult.success(resp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增剧集")
    public CommonResult<TvshowShowCreateResp> create(@RequestBody TvshowShowCreateReq req) {
        return super.create(req, TvshowShowConvert.INSTANCE::convertToDTO, TvshowShowConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑剧集")
    public CommonResult<Boolean> update(@RequestBody TvshowShowUpdateReq req) {
        return super.update(req, TvshowShowConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除剧集")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TvshowShowExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出剧集")
    public void export(TvshowShowPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "剧集" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TvshowShowExp.class, TvshowShowConvert.INSTANCE::convertToDTO, TvshowShowConvert.INSTANCE::convertToExp, response);
    }

}