package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.ThreadConvert;
import cc.onelooker.kaleido.dto.ThreadDTO;
import cc.onelooker.kaleido.dto.req.ThreadCreateReq;
import cc.onelooker.kaleido.dto.req.ThreadPageReq;
import cc.onelooker.kaleido.dto.req.ThreadUpdateReq;
import cc.onelooker.kaleido.dto.resp.ThreadCreateResp;
import cc.onelooker.kaleido.dto.resp.ThreadPageResp;
import cc.onelooker.kaleido.dto.resp.ThreadViewResp;
import cc.onelooker.kaleido.service.ThreadService;
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
 * 发布记录前端控制器
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */

@Api(tags = "发布记录")
@RestController
@RequestMapping("/thread")
public class ThreadController extends AbstractCrudController<ThreadDTO> {

    @Autowired
    private ThreadService threadService;

    @Override
    protected IBaseService getService() {
        return threadService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询发布记录")
    public CommonResult<PageResult<ThreadPageResp>> page(ThreadPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, ThreadConvert.INSTANCE::convertToDTO, ThreadConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看发布记录详情")
    public CommonResult<ThreadViewResp> view(String id) {
        return super.view(id, ThreadConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增发布记录")
    public CommonResult<ThreadCreateResp> create(@RequestBody ThreadCreateReq req) {
        return super.create(req, ThreadConvert.INSTANCE::convertToDTO, ThreadConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑发布记录")
    public CommonResult<Boolean> update(@RequestBody ThreadUpdateReq req) {
        return super.update(req, ThreadConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除发布记录")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

}