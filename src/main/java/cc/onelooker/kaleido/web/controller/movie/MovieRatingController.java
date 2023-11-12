package cc.onelooker.kaleido.web.controller.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.movie.MovieRatingService;
import cc.onelooker.kaleido.dto.movie.MovieRatingDTO;
import cc.onelooker.kaleido.convert.movie.MovieRatingConvert;
import cc.onelooker.kaleido.dto.movie.req.*;
import cc.onelooker.kaleido.dto.movie.resp.*;
import cc.onelooker.kaleido.dto.movie.exp.MovieRatingExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;


/**
* 电影评分前端控制器
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/

@Api(tags = "电影评分")
@RestController
@RequestMapping("/movieRating")
public class MovieRatingController extends AbstractCrudController<MovieRatingDTO>{

    @Autowired
    private MovieRatingService movieRatingService;

    @Override
    protected IBaseService getService() {
        return movieRatingService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影评分")
    public CommonResult<PageResult<MovieRatingPageResp>> page(MovieRatingPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieRatingConvert.INSTANCE::convertToDTO, MovieRatingConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影评分详情")
    public CommonResult<MovieRatingViewResp> view(Long id) {
        return super.view(id, MovieRatingConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影评分")
    public CommonResult<MovieRatingCreateResp> create(@RequestBody MovieRatingCreateReq req) {
        return super.create(req, MovieRatingConvert.INSTANCE::convertToDTO, MovieRatingConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影评分")
    public CommonResult<Boolean> update(@RequestBody MovieRatingUpdateReq req) {
        return super.update(req, MovieRatingConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影评分")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieRatingExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影评分")
    public void export(MovieRatingPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影评分" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieRatingExp.class,
                    MovieRatingConvert.INSTANCE::convertToDTO, MovieRatingConvert.INSTANCE::convertToExp, response);
    }

}