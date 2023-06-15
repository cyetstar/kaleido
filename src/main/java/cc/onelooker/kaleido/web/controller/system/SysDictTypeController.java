package cc.onelooker.kaleido.web.controller.system;

import cc.onelooker.kaleido.dto.system.SysUserDTO;
import com.zjjcnt.common.core.dict.Dictionary;
import com.zjjcnt.common.core.dict.DictionaryInitializer;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import cc.onelooker.kaleido.convert.system.SysDictTypeConvert;
import cc.onelooker.kaleido.dto.system.SysDictTypeDTO;
import cc.onelooker.kaleido.dto.system.req.SysDictTypeCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysDictTypePageReq;
import cc.onelooker.kaleido.dto.system.req.SysDictTypeUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysDictTypeCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysDictTypePageResp;
import cc.onelooker.kaleido.dto.system.resp.SysDictTypeViewResp;
import cc.onelooker.kaleido.service.system.SysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 字典表类型表前端控制器
 *
 * @author xiadawei
 * @date 2022-11-13 00:43:42
 */

@Api(tags = "字典表类型表")
@RestController
@RequestMapping("/sysDictType")
public class SysDictTypeController extends AbstractCrudController<SysDictTypeDTO> {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Autowired
    private Dictionary dictionary;

    @Override
    protected IBaseService getService() {
        return sysDictTypeService;
    }

    @GetMapping("page")
    @ApiOperation(value = "字典表类型表分页查询")
    public CommonResult<PageResult<SysDictTypePageResp>> page(SysDictTypePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, SysDictTypeConvert.INSTANCE::convertToDTO, SysDictTypeConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "字典表类型表详细信息")
    public CommonResult<SysDictTypeViewResp> view(Long id) {
        return super.view(id, SysDictTypeConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增字典表类型表")
    public CommonResult<SysDictTypeCreateResp> create(@RequestBody SysDictTypeCreateReq req) {
        return super.create(req, SysDictTypeConvert.INSTANCE::convertToDTO, SysDictTypeConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑字典表类型表")
    public CommonResult<Boolean> update(@RequestBody SysDictTypeUpdateReq req) {
        return CommonResult.success((sysDictTypeService.updateById(req)));
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除字典类型表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @ApiOperation(value = "重载字典")
    @GetMapping(value = "/reload")
    public CommonResult<Boolean> reload() {
        dictionary.loadAll();
        return CommonResult.success(true);
    }

    @GetMapping("checkUnique")
    @ApiOperation(value = "校验用户名唯一性")
    public CommonResult<Boolean> checkUnique(@RequestParam String type) {
        SysDictTypeDTO sysDictTypeDTO = sysDictTypeService.findByType(type);
        return CommonResult.success(sysDictTypeDTO == null);
    }

}