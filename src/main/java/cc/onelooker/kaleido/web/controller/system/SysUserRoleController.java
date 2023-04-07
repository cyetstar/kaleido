package cc.onelooker.kaleido.web.controller.system;

import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import cc.onelooker.kaleido.convert.system.SysUserRoleConvert;
import cc.onelooker.kaleido.dto.system.SysUserRoleDTO;
import cc.onelooker.kaleido.dto.system.req.SysUserRoleCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysUserRolePageReq;
import cc.onelooker.kaleido.dto.system.req.SysUserRoleUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysUserRoleCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysUserRolePageResp;
import cc.onelooker.kaleido.dto.system.resp.SysUserRoleViewResp;
import cc.onelooker.kaleido.service.system.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* 用户和角色关系表前端控制器
*
* @author xiadawei
* @date 2022-11-13 00:43:42
*/

@Api(tags = "用户和角色关系表")
@RestController
@RequestMapping("/sysUserRole")
public class SysUserRoleController extends AbstractCrudController<SysUserRoleDTO>{

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    protected IBaseService getService() {
        return sysUserRoleService;
    }

    @GetMapping("page")
    @ApiOperation(value = "用户和角色关系表分页查询", hidden = true)
    public CommonResult<PageResult<SysUserRolePageResp>> page(SysUserRolePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, SysUserRoleConvert.INSTANCE::convertToDTO, SysUserRoleConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "用户和角色关系表详细信息", hidden = true)
    public CommonResult<SysUserRoleViewResp> view(Long id) {
        return super.view(id, SysUserRoleConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增用户和角色关系表", hidden = true)
    public CommonResult<SysUserRoleCreateResp> create(@RequestBody SysUserRoleCreateReq req) {
        return super.create(req, SysUserRoleConvert.INSTANCE::convertToDTO, SysUserRoleConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑用户和角色关系表", hidden = true)
    public CommonResult<Boolean> update(@RequestBody SysUserRoleUpdateReq req) {
        return super.update(req, SysUserRoleConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除用户和角色关系表", hidden = true)
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

}