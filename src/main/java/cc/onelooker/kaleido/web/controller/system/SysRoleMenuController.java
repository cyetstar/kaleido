package cc.onelooker.kaleido.web.controller.system;

import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import cc.onelooker.kaleido.convert.system.SysRoleMenuConvert;
import cc.onelooker.kaleido.dto.system.SysRoleMenuDTO;
import cc.onelooker.kaleido.dto.system.req.SysRoleMenuCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysRoleMenuPageReq;
import cc.onelooker.kaleido.dto.system.req.SysRoleMenuUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysRoleMenuCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysRoleMenuPageResp;
import cc.onelooker.kaleido.dto.system.resp.SysRoleMenuViewResp;
import cc.onelooker.kaleido.service.system.SysRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* 角色和菜单关系表前端控制器
*
* @author xiadawei
* @date 2022-11-13 01:12:24
*/

@Api(tags = "角色和菜单关系表")
@RestController
@RequestMapping("/sysRoleMenu")
public class SysRoleMenuController extends AbstractCrudController<SysRoleMenuDTO>{

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    protected IBaseService getService() {
        return sysRoleMenuService;
    }

    @GetMapping("page")
    @ApiOperation(value = "角色和菜单关系表分页查询", hidden = true)
    public CommonResult<PageResult<SysRoleMenuPageResp>> page(SysRoleMenuPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, SysRoleMenuConvert.INSTANCE::convertToDTO, SysRoleMenuConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "角色和菜单关系表详细信息", hidden = true)
    public CommonResult<SysRoleMenuViewResp> view(Long id) {
        return super.view(id, SysRoleMenuConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增角色和菜单关系表", hidden = true)
    public CommonResult<SysRoleMenuCreateResp> create(@RequestBody SysRoleMenuCreateReq req) {
        return super.create(req, SysRoleMenuConvert.INSTANCE::convertToDTO, SysRoleMenuConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑角色和菜单关系表", hidden = true)
    public CommonResult<Boolean> update(@RequestBody SysRoleMenuUpdateReq req) {
        return super.update(req, SysRoleMenuConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除角色和菜单关系表", hidden = true)
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

}