package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.AlternateTitleConvert;
import cc.onelooker.kaleido.dto.AlternateTitleDTO;
import cc.onelooker.kaleido.dto.req.AlternateTitleCreateReq;
import cc.onelooker.kaleido.dto.req.AlternateTitlePageReq;
import cc.onelooker.kaleido.dto.req.AlternateTitleUpdateReq;
import cc.onelooker.kaleido.dto.resp.AlternateTitleCreateResp;
import cc.onelooker.kaleido.dto.resp.AlternateTitlePageResp;
import cc.onelooker.kaleido.dto.resp.AlternateTitleViewResp;
import cc.onelooker.kaleido.service.AlternateTitleService;
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
* 别名前端控制器
*
* @author cyetstar
* @date 2024-03-12 17:48:21
*/

@Api(tags = "别名")
@RestController
@RequestMapping("/alternateTitle")
public class AlternateTitleController extends AbstractCrudController<AlternateTitleDTO>{

    @Autowired
    private AlternateTitleService alternateTitleService;

    @Override
    protected IBaseService getService() {
        return alternateTitleService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询别名")
    public CommonResult<PageResult<AlternateTitlePageResp>> page(AlternateTitlePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, AlternateTitleConvert.INSTANCE::convertToDTO, AlternateTitleConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看别名详情")
    public CommonResult<AlternateTitleViewResp> view(Long id) {
        return super.view(id, AlternateTitleConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增别名")
    public CommonResult<AlternateTitleCreateResp> create(@RequestBody AlternateTitleCreateReq req) {
        return super.create(req, AlternateTitleConvert.INSTANCE::convertToDTO, AlternateTitleConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑别名")
    public CommonResult<Boolean> update(@RequestBody AlternateTitleUpdateReq req) {
        return super.update(req, AlternateTitleConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除别名")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }


}