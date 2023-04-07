package cc.onelooker.kaleido.web.controller.system;

import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import cc.onelooker.kaleido.convert.system.SysConfigConvert;
import cc.onelooker.kaleido.dto.system.SysConfigDTO;
import cc.onelooker.kaleido.dto.system.req.SysConfigCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysConfigPageReq;
import cc.onelooker.kaleido.dto.system.req.SysConfigUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysConfigCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysConfigPageResp;
import cc.onelooker.kaleido.dto.system.resp.SysConfigViewResp;
import cc.onelooker.kaleido.service.system.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* 系统配置表前端控制器
*
* @author xiadawei
* @date 2022-11-13 00:43:42
*/

@Api(tags = "系统配置表")
@RestController
@RequestMapping("/sysConfig")
public class SysConfigController extends AbstractCrudController<SysConfigDTO>{

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    protected IBaseService getService() {
        return sysConfigService;
    }

    @GetMapping("page")
    @ApiOperation(value = "系统配置表分页查询", hidden = true)
    public CommonResult<PageResult<SysConfigPageResp>> page(SysConfigPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, SysConfigConvert.INSTANCE::convertToDTO, SysConfigConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "系统配置表详细信息", hidden = true)
    public CommonResult<SysConfigViewResp> view(Long id) {
        return super.view(id, SysConfigConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增系统配置表", hidden = true)
    public CommonResult<SysConfigCreateResp> create(@RequestBody SysConfigCreateReq req) {
        return super.create(req, SysConfigConvert.INSTANCE::convertToDTO, SysConfigConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑系统配置表", hidden = true)
    public CommonResult<Boolean> update(@RequestBody SysConfigUpdateReq req) {
        return super.update(req, SysConfigConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除系统配置表", hidden = true)
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

}