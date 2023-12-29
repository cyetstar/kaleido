package cc.onelooker.kaleido.web.controller.trade;

import cc.onelooker.kaleido.convert.trade.TradeLogConvert;
import cc.onelooker.kaleido.dto.trade.TradeAccountDTO;
import cc.onelooker.kaleido.dto.trade.TradeLogDTO;
import cc.onelooker.kaleido.dto.trade.req.TradeLogCreateReq;
import cc.onelooker.kaleido.dto.trade.req.TradeLogPageReq;
import cc.onelooker.kaleido.dto.trade.req.TradeLogUpdateReq;
import cc.onelooker.kaleido.dto.trade.resp.TradeLogCreateResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeLogPageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeLogViewResp;
import cc.onelooker.kaleido.service.trade.TradeAccountService;
import cc.onelooker.kaleido.service.trade.TradeLogService;
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
 * 交易记录前端控制器
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */

@Api(tags = "交易记录")
@RestController
@RequestMapping("/tradeLog")
public class TradeLogController extends AbstractCrudController<TradeLogDTO> {

    @Autowired
    private TradeLogService tradeLogService;

    @Autowired
    private TradeAccountService tradeAccountService;

    @Override
    protected IBaseService getService() {
        return tradeLogService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询交易记录")
    public CommonResult<PageResult<TradeLogPageResp>> page(TradeLogPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TradeLogConvert.INSTANCE::convertToDTO, TradeLogConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看交易记录详情")
    public CommonResult<TradeLogViewResp> view(Long id) {
        return super.view(id, TradeLogConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增交易记录")
    public CommonResult<TradeLogCreateResp> create(@RequestBody TradeLogCreateReq req) {
        if (req.getAccountId() == null) {
            TradeAccountDTO tradeAccountDTO = tradeAccountService.find((TradeAccountDTO) null);
            if (tradeAccountDTO != null) {
                req.setAccountId(tradeAccountDTO.getId());
            }
        }
        return super.create(req, TradeLogConvert.INSTANCE::convertToDTO, TradeLogConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑交易记录")
    public CommonResult<Boolean> update(@RequestBody TradeLogUpdateReq req) {
        return super.update(req, TradeLogConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除交易记录")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

}