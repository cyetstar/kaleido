package cc.onelooker.kaleido.web.controller.system;

import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.ExportColumn;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.convert.system.SysRoleConvert;
import cc.onelooker.kaleido.dto.system.SysRoleDTO;
import cc.onelooker.kaleido.dto.system.exp.SysRoleExp;
import cc.onelooker.kaleido.dto.system.req.SysRoleAuthorizeReq;
import cc.onelooker.kaleido.dto.system.req.SysRoleCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysRolePageReq;
import cc.onelooker.kaleido.dto.system.req.SysRoleUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysRoleCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysRolePageResp;
import cc.onelooker.kaleido.dto.system.resp.SysRoleViewResp;
import cc.onelooker.kaleido.service.system.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 角色表前端控制器
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */

@Api(tags = "角色表")
@RestController
@RequestMapping("/sysRole")
public class SysRoleController extends AbstractCrudController<SysRoleDTO> {

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    protected IBaseService getService() {
        return sysRoleService;
    }

    @GetMapping("page")
    @ApiOperation(value = "角色表分页查询", hidden = true)
    public CommonResult<PageResult<SysRolePageResp>> page(SysRolePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, SysRoleConvert.INSTANCE::convertToDTO, SysRoleConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "角色表详细信息", hidden = true)
    public CommonResult<SysRoleViewResp> view(Long id) {
        return super.view(id, SysRoleConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增角色表", hidden = true)
    public CommonResult<SysRoleCreateResp> create(@RequestBody SysRoleCreateReq req) {
        return super.create(req, SysRoleConvert.INSTANCE::convertToDTO, SysRoleConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑角色表", hidden = true)
    public CommonResult<Boolean> update(@RequestBody SysRoleUpdateReq req) {
        return super.update(req, SysRoleConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除角色表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(SysRoleExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出数据")
    public void export(SysRolePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "角色表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, SysRoleExp.class,
                SysRoleConvert.INSTANCE::convertToDTO, SysRoleConvert.INSTANCE::convertToExp, response);
    }

    @PostMapping("authorize")
    @ApiOperation(value = "角色授权")
    public CommonResult<Boolean> authorize(@RequestBody SysRoleAuthorizeReq req) {
        sysRoleService.authorize(req.getId(), req.getResourceIdList());
        return CommonResult.success(true);
    }


}