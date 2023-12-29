package cc.onelooker.kaleido.web.controller.trade;

import cc.onelooker.kaleido.convert.trade.TradeStrategyConvert;
import cc.onelooker.kaleido.dto.trade.TradeStrategyDTO;
import cc.onelooker.kaleido.dto.trade.req.*;
import cc.onelooker.kaleido.dto.trade.resp.TradeStrategyCreateResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeStrategyPageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeStrategyViewResp;
import cc.onelooker.kaleido.service.trade.TradeStrategyService;
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
 * 策略前端控制器
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */

@Api(tags = "策略")
@RestController
@RequestMapping("/tradeStrategy")
public class TradeStrategyController extends AbstractCrudController<TradeStrategyDTO> {

    @Autowired
    private TradeStrategyService tradeStrategyService;

    @Override
    protected IBaseService getService() {
        return tradeStrategyService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询策略")
    public CommonResult<PageResult<TradeStrategyPageResp>> page(TradeStrategyPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TradeStrategyConvert.INSTANCE::convertToDTO, TradeStrategyConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看策略详情")
    public CommonResult<TradeStrategyViewResp> view(Long id) {
        return super.view(id, TradeStrategyConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增策略")
    public CommonResult<TradeStrategyCreateResp> create(@RequestBody TradeStrategyCreateReq req) {
        return super.create(req, TradeStrategyConvert.INSTANCE::convertToDTO, TradeStrategyConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑策略")
    public CommonResult<Boolean> update(@RequestBody TradeStrategyUpdateReq req) {
        return super.update(req, TradeStrategyConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除策略")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @PostMapping("start")
    @ApiOperation(value = "启动策略")
    public CommonResult<Boolean> start(@RequestBody TradeStrategyStartReq req) {
        tradeStrategyService.start(req.getId());
        return CommonResult.success(true);
    }

    @PostMapping("stop")
    @ApiOperation(value = "停止策略")
    public CommonResult<Boolean> stop(@RequestBody TradeStrategyStopReq req) {
        tradeStrategyService.stop(req.getId());
        return CommonResult.success(true);
    }

}