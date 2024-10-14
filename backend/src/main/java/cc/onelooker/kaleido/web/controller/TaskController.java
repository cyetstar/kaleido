package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.TaskConvert;
import cc.onelooker.kaleido.dto.TaskDTO;
import cc.onelooker.kaleido.dto.req.TaskCreateReq;
import cc.onelooker.kaleido.dto.req.TaskPageReq;
import cc.onelooker.kaleido.dto.req.TaskUpdateReq;
import cc.onelooker.kaleido.dto.resp.TaskCreateResp;
import cc.onelooker.kaleido.dto.resp.TaskPageResp;
import cc.onelooker.kaleido.dto.resp.TaskViewResp;
import cc.onelooker.kaleido.service.TaskService;
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
* 任务前端控制器
*
* @author cyetstar
* @date 2024-06-27 17:18:04
*/

@Api(tags = "任务")
@RestController
@RequestMapping("/task")
public class TaskController extends AbstractCrudController<TaskDTO>{

    @Autowired
    private TaskService taskService;

    @Override
    protected IBaseService getService() {
        return taskService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询任务")
    public CommonResult<PageResult<TaskPageResp>> page(TaskPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TaskConvert.INSTANCE::convertToDTO, TaskConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看任务详情")
    public CommonResult<TaskViewResp> view(String id) {
        return super.view(id, TaskConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增任务")
    public CommonResult<TaskCreateResp> create(@RequestBody TaskCreateReq req) {
        return super.create(req, TaskConvert.INSTANCE::convertToDTO, TaskConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑任务")
    public CommonResult<Boolean> update(@RequestBody TaskUpdateReq req) {
        return super.update(req, TaskConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除任务")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

}