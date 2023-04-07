package cc.onelooker.kaleido.web.controller.system;

import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.ExportColumn;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.convert.system.SysDictConvert;
import cc.onelooker.kaleido.dto.system.SysDictDTO;
import cc.onelooker.kaleido.dto.system.exp.SysDictExp;
import cc.onelooker.kaleido.dto.system.req.SysDictCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysDictPageReq;
import cc.onelooker.kaleido.dto.system.req.SysDictUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysDictPageResp;
import cc.onelooker.kaleido.dto.system.resp.SysDictViewResp;
import cc.onelooker.kaleido.service.system.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 字典表前端控制器
 *
 * @author xiadawei
 * @date 2022-11-19 23:17:28
 */

@Api(tags = "字典表")
@RestController
@RequestMapping("/sysDict")
public class SysDictController extends AbstractCrudController<SysDictDTO> {

    @Autowired
    private SysDictService sysDictService;

    @Override
    protected IBaseService getService() {
        return sysDictService;
    }

    @GetMapping("page")
    @ApiOperation(value = "字典表分页查询")
    public CommonResult<PageResult<SysDictPageResp>> page(SysDictPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, SysDictConvert.INSTANCE::convertToDTO, SysDictConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "字典表详细信息")
    public CommonResult<SysDictViewResp> view(Long id) {
        return super.view(id, SysDictConvert.INSTANCE::convertToViewResp);
    }

//    @PostMapping("create")
//    @ApiOperation(value = "新增字典表")
//    public CommonResult<Boolean> create(@RequestBody SysDictCreateReq req) {
//        return CommonResult.success(sysDictService.create(req));
//    }

    @PostMapping("create")
    @ApiOperation(value = "新增字典表")
    public CommonResult<Boolean> create(@RequestBody List<SysDictCreateReq> reqs) {
        return CommonResult.success(sysDictService.createBatch(reqs));
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑字典表")
    public CommonResult<Boolean> update(@RequestBody SysDictUpdateReq req) {
        return CommonResult.success(sysDictService.updateById(req));
    }

    @ApiOperation(value = "查询某一类型字典")
    @GetMapping(value = "/dictType/{dictType}")
    public CommonResult<List<SysDictDTO>> listByDictType(@PathVariable String dictType) {
        return CommonResult.success(sysDictService.listBySysDictType(dictType));
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除字典表")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(SysDictExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出数据")
    public void export(SysDictPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "字典表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, SysDictExp.class,
                SysDictConvert.INSTANCE::convertToDTO, SysDictConvert.INSTANCE::convertToExp, response);
    }

    @ApiOperation(value = "查询所有")
    @GetMapping(value = "/listAll")
    public CommonResult<List<SysDictPageResp>> listAll(SysDictPageReq req) {
        return CommonResult.success(sysDictService.listAll(req));
    }

}