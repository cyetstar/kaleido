package cc.onelooker.kaleido.web.controller.trade;

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
import cc.onelooker.kaleido.service.trade.TradeLogService;
import cc.onelooker.kaleido.dto.trade.TradeLogDTO;
import cc.onelooker.kaleido.convert.trade.TradeLogConvert;
import cc.onelooker.kaleido.dto.trade.req.*;
import cc.onelooker.kaleido.dto.trade.resp.*;
import cc.onelooker.kaleido.exp.trade.TradeLogExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;

import com.zjjcnt.common.core.annotation.Dict;

import java.lang.String;
import java.math.BigDecimal;

import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

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

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TradeLogExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出交易记录")
    public void export(TradeLogPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "交易记录" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TradeLogExp.class, TradeLogConvert.INSTANCE::convertToDTO, TradeLogConvert.INSTANCE::convertToExp, response);
    }

}