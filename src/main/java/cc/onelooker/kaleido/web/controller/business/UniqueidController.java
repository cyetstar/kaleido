package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.UniqueidService;
import cc.onelooker.kaleido.dto.business.UniqueidDTO;
import cc.onelooker.kaleido.convert.business.UniqueidConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.UniqueidExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;


/**
* 前端控制器
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/

@Api(tags = "")
@RestController
@RequestMapping("/uniqueid")
public class UniqueidController extends AbstractCrudController<UniqueidDTO>{

    @Autowired
    private UniqueidService uniqueidService;

    @Override
    protected IBaseService getService() {
        return uniqueidService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询")
    public CommonResult<PageResult<UniqueidPageResp>> page(UniqueidPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, UniqueidConvert.INSTANCE::convertToDTO, UniqueidConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看详情")
    public CommonResult<UniqueidViewResp> view(Long id) {
        return super.view(id, UniqueidConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增")
    public CommonResult<UniqueidCreateResp> create(@RequestBody UniqueidCreateReq req) {
        return super.create(req, UniqueidConvert.INSTANCE::convertToDTO, UniqueidConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑")
    public CommonResult<Boolean> update(@RequestBody UniqueidUpdateReq req) {
        return super.update(req, UniqueidConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(UniqueidExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出")
    public void export(UniqueidPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, UniqueidExp.class,
                    UniqueidConvert.INSTANCE::convertToDTO, UniqueidConvert.INSTANCE::convertToExp, response);
    }

}