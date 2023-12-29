package cc.onelooker.kaleido.web.controller.trade;

import cc.onelooker.kaleido.convert.trade.TradeAccountConvert;
import cc.onelooker.kaleido.dto.trade.TradeAccountDTO;
import cc.onelooker.kaleido.dto.trade.req.TradeAccountCreateReq;
import cc.onelooker.kaleido.dto.trade.req.TradeAccountPageReq;
import cc.onelooker.kaleido.dto.trade.req.TradeAccountSaveReq;
import cc.onelooker.kaleido.dto.trade.req.TradeAccountUpdateReq;
import cc.onelooker.kaleido.dto.trade.resp.TradeAccountCreateResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeAccountGetBalanceResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeAccountPageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeAccountViewResp;
import cc.onelooker.kaleido.service.trade.TradeAccountService;
import cc.onelooker.kaleido.third.mexc.MexcApiService;
import cc.onelooker.kaleido.third.mexc.resp.AccountResp;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 交易账户前端控制器
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */

@Api(tags = "交易账户")
@RestController
@RequestMapping("/tradeAccount")
public class TradeAccountController extends AbstractCrudController<TradeAccountDTO> {

    @Autowired
    private TradeAccountService tradeAccountService;

    @Autowired
    private MexcApiService mexcApiService;

    @Override
    protected IBaseService getService() {
        return tradeAccountService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询交易账户")
    public CommonResult<PageResult<TradeAccountPageResp>> page(TradeAccountPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TradeAccountConvert.INSTANCE::convertToDTO, TradeAccountConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看交易账户详情")
    public CommonResult<TradeAccountViewResp> view() {
        TradeAccountDTO tradeAccountDTO = tradeAccountService.find((TradeAccountDTO) null);
        TradeAccountViewResp resp = TradeAccountConvert.INSTANCE.convertToViewResp(tradeAccountDTO);
        return CommonResult.success(resp);
    }

    @PostMapping("save")
    @ApiOperation(value = "保存交易账户")
    public CommonResult<Boolean> save(@RequestBody TradeAccountSaveReq req) {
        TradeAccountDTO tradeAccountDTO = TradeAccountConvert.INSTANCE.convertToDTO(req);
        if (tradeAccountDTO.getDqye() == null) {
            tradeAccountDTO.setDqye(tradeAccountDTO.getCsye());
        }
        if (tradeAccountDTO.getId() == null) {
            tradeAccountService.insert(tradeAccountDTO);
        } else {
            tradeAccountService.update(tradeAccountDTO);
        }
        return CommonResult.success(true);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增交易账户")
    public CommonResult<TradeAccountCreateResp> create(@RequestBody TradeAccountCreateReq req) {
        return super.create(req, TradeAccountConvert.INSTANCE::convertToDTO, TradeAccountConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑交易账户")
    public CommonResult<Boolean> update(@RequestBody TradeAccountUpdateReq req) {
        return super.update(req, TradeAccountConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除交易账户")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/getBalance")
    public CommonResult<TradeAccountGetBalanceResp> getBalance(@RequestParam(defaultValue = "USDT") String jjb) {
        AccountResp account = mexcApiService.account();
        String balance = account.getBalances().stream().filter(s -> StringUtils.equals(s.getAsset(), jjb)).map(s -> s.getFree()).findFirst().orElse("0");
        TradeAccountGetBalanceResp resp = new TradeAccountGetBalanceResp();
        resp.setJjb(jjb);
        resp.setYe("1000");
        return CommonResult.success(resp);
    }

}