package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.ThumbService;
import cc.onelooker.kaleido.dto.business.ThumbDTO;
import cc.onelooker.kaleido.convert.business.ThumbConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.ThumbExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;


/**
* 前端控制器
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/

@Api(tags = "")
@RestController
@RequestMapping("/thumb")
public class ThumbController extends AbstractCrudController<ThumbDTO>{

    @Autowired
    private ThumbService thumbService;

    @Override
    protected IBaseService getService() {
        return thumbService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询")
    public CommonResult<PageResult<ThumbPageResp>> page(ThumbPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, ThumbConvert.INSTANCE::convertToDTO, ThumbConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看详情")
    public CommonResult<ThumbViewResp> view(Long id) {
        return super.view(id, ThumbConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增")
    public CommonResult<ThumbCreateResp> create(@RequestBody ThumbCreateReq req) {
        return super.create(req, ThumbConvert.INSTANCE::convertToDTO, ThumbConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑")
    public CommonResult<Boolean> update(@RequestBody ThumbUpdateReq req) {
        return super.update(req, ThumbConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(ThumbExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出")
    public void export(ThumbPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, ThumbExp.class,
                    ThumbConvert.INSTANCE::convertToDTO, ThumbConvert.INSTANCE::convertToExp, response);
    }

}