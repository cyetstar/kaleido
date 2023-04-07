package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.FilenameService;
import cc.onelooker.kaleido.dto.business.FilenameDTO;
import cc.onelooker.kaleido.convert.business.FilenameConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.FilenameExp;

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
@RequestMapping("/filename")
public class FilenameController extends AbstractCrudController<FilenameDTO>{

    @Autowired
    private FilenameService filenameService;

    @Override
    protected IBaseService getService() {
        return filenameService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询")
    public CommonResult<PageResult<FilenamePageResp>> page(FilenamePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, FilenameConvert.INSTANCE::convertToDTO, FilenameConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看详情")
    public CommonResult<FilenameViewResp> view(Long id) {
        return super.view(id, FilenameConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增")
    public CommonResult<FilenameCreateResp> create(@RequestBody FilenameCreateReq req) {
        return super.create(req, FilenameConvert.INSTANCE::convertToDTO, FilenameConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑")
    public CommonResult<Boolean> update(@RequestBody FilenameUpdateReq req) {
        return super.update(req, FilenameConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(FilenameExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出")
    public void export(FilenamePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, FilenameExp.class,
                    FilenameConvert.INSTANCE::convertToDTO, FilenameConvert.INSTANCE::convertToExp, response);
    }

}