package cc.onelooker.kaleido.web.controller.trade;

import cc.onelooker.kaleido.mexc.resp.AccountResp;
import cc.onelooker.kaleido.mexc.MexcApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.trade.TradeAccountService;
import cc.onelooker.kaleido.dto.trade.TradeAccountDTO;
import cc.onelooker.kaleido.convert.trade.TradeAccountConvert;
import cc.onelooker.kaleido.dto.trade.req.*;
import cc.onelooker.kaleido.dto.trade.resp.*;
import cc.onelooker.kaleido.exp.trade.TradeAccountExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;

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

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TradeAccountExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出交易账户")
    public void export(TradeAccountPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "交易账户" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TradeAccountExp.class, TradeAccountConvert.INSTANCE::convertToDTO, TradeAccountConvert.INSTANCE::convertToExp, response);
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