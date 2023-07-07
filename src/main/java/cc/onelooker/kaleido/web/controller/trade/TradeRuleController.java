package cc.onelooker.kaleido.web.controller.trade;

import cc.onelooker.kaleido.convert.trade.TradeAccountConvert;
import cc.onelooker.kaleido.dto.trade.TradeAccountDTO;
import cc.onelooker.kaleido.service.trade.TradeAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.trade.TradeRuleService;
import cc.onelooker.kaleido.dto.trade.TradeRuleDTO;
import cc.onelooker.kaleido.convert.trade.TradeRuleConvert;
import cc.onelooker.kaleido.dto.trade.req.*;
import cc.onelooker.kaleido.dto.trade.resp.*;
import cc.onelooker.kaleido.exp.trade.TradeRuleExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.String;

/**
 * 交易规则前端控制器
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 */

@Api(tags = "交易规则")
@RestController
@RequestMapping("/tradeRule")
public class TradeRuleController extends AbstractCrudController<TradeRuleDTO> {

    @Autowired
    private TradeRuleService tradeRuleService;

    @Autowired
    private TradeAccountService tradeAccountService;

    @Override
    protected IBaseService getService() {
        return tradeRuleService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询交易规则")
    public CommonResult<PageResult<TradeRulePageResp>> page(TradeRulePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TradeRuleConvert.INSTANCE::convertToDTO, TradeRuleConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看交易规则详情")
    public CommonResult<TradeRuleViewResp> view() {
        TradeRuleDTO tradeRuleDTO = tradeRuleService.find((TradeRuleDTO) null);
        TradeRuleViewResp resp = TradeRuleConvert.INSTANCE.convertToViewResp(tradeRuleDTO);
        return CommonResult.success(resp);
    }

    @PostMapping("save")
    @ApiOperation(value = "保存交易规则")
    public CommonResult<Boolean> save(@RequestBody TradeRuleSaveReq req) {
        TradeRuleDTO tradeRuleDTO = TradeRuleConvert.INSTANCE.convertToDTO(req);
        if (tradeRuleDTO.getAccountId() == null) {
            TradeAccountDTO tradeAccountDTO = tradeAccountService.find((TradeAccountDTO) null);
            if (tradeAccountDTO != null) {
                tradeRuleDTO.setAccountId(tradeAccountDTO.getId());
            }
        }
        if (tradeRuleDTO.getId() == null) {
            tradeRuleService.insert(tradeRuleDTO);
        } else {
            tradeRuleService.update(tradeRuleDTO);
        }
        return CommonResult.success(true);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增交易规则")
    public CommonResult<TradeRuleCreateResp> create(@RequestBody TradeRuleCreateReq req) {
        return super.create(req, TradeRuleConvert.INSTANCE::convertToDTO, TradeRuleConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑交易规则")
    public CommonResult<Boolean> update(@RequestBody TradeRuleUpdateReq req) {
        return super.update(req, TradeRuleConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除交易规则")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TradeRuleExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出交易规则")
    public void export(TradeRulePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "交易规则" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TradeRuleExp.class, TradeRuleConvert.INSTANCE::convertToDTO, TradeRuleConvert.INSTANCE::convertToExp, response);
    }

}