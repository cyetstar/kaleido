package cc.onelooker.kaleido.web.controller.system;

import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import cc.onelooker.kaleido.convert.system.SysRoleResourceConvert;
import cc.onelooker.kaleido.dto.system.SysRoleResourceDTO;
import cc.onelooker.kaleido.dto.system.req.SysRoleResourceBindReq;
import cc.onelooker.kaleido.dto.system.req.SysRoleResourceCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysRoleResourcePageReq;
import cc.onelooker.kaleido.dto.system.req.SysRoleResourceUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysRoleResourceCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysRoleResourcePageResp;
import cc.onelooker.kaleido.dto.system.resp.SysRoleResourceResourcesResp;
import cc.onelooker.kaleido.dto.system.resp.SysRoleResourceViewResp;
import cc.onelooker.kaleido.service.system.SysRoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色和资源关系表前端控制器
 *
 * @author xiadawei
 * @date 2022-11-13 00:43:42
 */

@Api(tags = "角色和资源关系表")
@RestController
@RequestMapping("/sysRoleResource")
public class SysRoleResourceController extends AbstractCrudController<SysRoleResourceDTO> {

    @Autowired
    private SysRoleResourceService sysRoleResourceService;

    @Override
    protected IBaseService getService() {
        return sysRoleResourceService;
    }

    @GetMapping("page")
    @ApiOperation(value = "角色和资源关系表分页查询", hidden = true)
    public CommonResult<PageResult<SysRoleResourcePageResp>> page(SysRoleResourcePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, SysRoleResourceConvert.INSTANCE::convertToDTO, SysRoleResourceConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "角色和资源关系表详细信息", hidden = true)
    public CommonResult<SysRoleResourceViewResp> view(Long id) {
        return super.view(id, SysRoleResourceConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增角色和资源关系表", hidden = true)
    public CommonResult<SysRoleResourceCreateResp> create(@RequestBody SysRoleResourceCreateReq req) {
        return super.create(req, SysRoleResourceConvert.INSTANCE::convertToDTO, SysRoleResourceConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑角色和资源关系表", hidden = true)
    public CommonResult<Boolean> update(@RequestBody SysRoleResourceUpdateReq req) {
        return super.update(req, SysRoleResourceConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除角色和资源关系表", hidden = true)
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @PostMapping("bind")
    @ApiOperation("绑定角色资源")
    public CommonResult<Boolean> bind(@RequestBody @Validated SysRoleResourceBindReq req) {
        return CommonResult.success(sysRoleResourceService.bind(req));
    }

}