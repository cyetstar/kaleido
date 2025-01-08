package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.SysDeptConvert;
import cc.onelooker.kaleido.dto.SysDeptDTO;
import cc.onelooker.kaleido.dto.req.SysDeptCreateReq;
import cc.onelooker.kaleido.dto.req.SysDeptPageReq;
import cc.onelooker.kaleido.dto.req.SysDeptUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysDeptCreateResp;
import cc.onelooker.kaleido.dto.resp.SysDeptPageResp;
import cc.onelooker.kaleido.dto.resp.SysDeptViewResp;
import cc.onelooker.kaleido.service.SysDeptService;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 部门表前端控制器
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 */

@Api(tags = "部门表")
@RestController
@RequestMapping("/sysDept")
public class SysDeptController extends AbstractCrudController<SysDeptDTO> {

    @Autowired
    private SysDeptService sysDeptService;

    @Override
    protected IBaseService<SysDeptDTO> getService() {
        return sysDeptService;
    }

    @GetMapping("page")
    @ApiOperation(value = "部门表分页查询", hidden = true)
    public CommonResult<PageResult<SysDeptPageResp>> page(SysDeptPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, SysDeptConvert.INSTANCE::convertToDTO,
                SysDeptConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "部门表详细信息", hidden = true)
    public CommonResult<SysDeptViewResp> view(Long id) {
        return super.view(id, SysDeptConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增部门表", hidden = true)
    public CommonResult<SysDeptCreateResp> create(@RequestBody SysDeptCreateReq req) {
        return super.create(req, SysDeptConvert.INSTANCE::convertToDTO, SysDeptConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑部门表", hidden = true)
    public CommonResult<Boolean> update(@RequestBody SysDeptUpdateReq req) {
        return super.update(req, SysDeptConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除部门表", hidden = true)
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

}