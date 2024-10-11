package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.AttributeConvert;
import cc.onelooker.kaleido.dto.AttributeDTO;
import cc.onelooker.kaleido.dto.req.AttributeCreateReq;
import cc.onelooker.kaleido.dto.req.AttributePageReq;
import cc.onelooker.kaleido.dto.req.AttributeUpdateReq;
import cc.onelooker.kaleido.dto.resp.AttributeCreateResp;
import cc.onelooker.kaleido.dto.resp.AttributePageResp;
import cc.onelooker.kaleido.dto.resp.AttributeViewResp;
import cc.onelooker.kaleido.service.AttributeService;
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
* 属性前端控制器
*
* @author cyetstar
* @date 2024-03-12 17:48:21
*/

@Api(tags = "属性")
@RestController
@RequestMapping("/attribute")
public class AttributeController extends AbstractCrudController<AttributeDTO>{

    @Autowired
    private AttributeService attributeService;

    @Override
    protected IBaseService getService() {
        return attributeService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询属性")
    public CommonResult<PageResult<AttributePageResp>> page(AttributePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, AttributeConvert.INSTANCE::convertToDTO, AttributeConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看属性详情")
    public CommonResult<AttributeViewResp> view(String id) {
        return super.view(id, AttributeConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增属性")
    public CommonResult<AttributeCreateResp> create(@RequestBody AttributeCreateReq req) {
        return super.create(req, AttributeConvert.INSTANCE::convertToDTO, AttributeConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑属性")
    public CommonResult<Boolean> update(@RequestBody AttributeUpdateReq req) {
        return super.update(req, AttributeConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除属性")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }


}