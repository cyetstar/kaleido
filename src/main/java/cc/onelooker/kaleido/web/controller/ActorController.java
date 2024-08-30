package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.ActorConvert;
import cc.onelooker.kaleido.dto.ActorDTO;
import cc.onelooker.kaleido.dto.req.ActorCreateReq;
import cc.onelooker.kaleido.dto.req.ActorPageReq;
import cc.onelooker.kaleido.dto.req.ActorUpdateReq;
import cc.onelooker.kaleido.dto.resp.ActorCreateResp;
import cc.onelooker.kaleido.dto.resp.ActorPageResp;
import cc.onelooker.kaleido.dto.resp.ActorViewResp;
import cc.onelooker.kaleido.service.ActorService;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 演职员前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */

@Api(tags = "演职员")
@RestController
@RequestMapping("/actor")
public class ActorController extends AbstractCrudController<ActorDTO> {

    @Autowired
    private ActorService actorService;

    @Override
    protected IBaseService getService() {
        return actorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询演职员")
    public CommonResult<PageResult<ActorPageResp>> page(ActorPageReq req, PageParam pageParam) {
        ActorDTO dto = ActorConvert.INSTANCE.convertToDTO(req);
        if (StringUtils.isNotEmpty(req.getIds())) {
            dto.setIdList(Arrays.asList(StringUtils.split(req.getIds(), Constants.COMMA)));
        }
        PageResult<ActorDTO> dtoPageResult = actorService.page(dto, pageParam.toPage());
        PageResult<ActorPageResp> pageResult = PageResult.convert(dtoPageResult, ActorConvert.INSTANCE::convertToPageResp);
        return CommonResult.success(pageResult);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看演职员详情")
    public CommonResult<ActorViewResp> view(String id) {
        return super.view(id, ActorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增演职员")
    public CommonResult<ActorCreateResp> create(@RequestBody ActorCreateReq req) {
        return super.create(req, ActorConvert.INSTANCE::convertToDTO, ActorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑演职员")
    public CommonResult<Boolean> update(@RequestBody ActorUpdateReq req) {
        return super.update(req, ActorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除演职员")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

}