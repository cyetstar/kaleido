package cc.onelooker.kaleido.web.controller.trade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.trade.TradeOrderService;
import cc.onelooker.kaleido.dto.trade.TradeOrderDTO;
import cc.onelooker.kaleido.convert.trade.TradeOrderConvert;
import cc.onelooker.kaleido.dto.trade.req.*;
import cc.onelooker.kaleido.dto.trade.resp.*;
import cc.onelooker.kaleido.exp.trade.TradeOrderExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;


/**
* 交易订单前端控制器
*
* @author cyetstar
* @date 2023-07-05 23:02:49
*/

@Api(tags = "交易订单")
@RestController
@RequestMapping("/tradeOrder")
public class TradeOrderController extends AbstractCrudController<TradeOrderDTO>{

    @Autowired
    private TradeOrderService tradeOrderService;

    @Override
    protected IBaseService getService() {
        return tradeOrderService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询交易订单")
    public CommonResult<PageResult<TradeOrderPageResp>> page(TradeOrderPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TradeOrderConvert.INSTANCE::convertToDTO, TradeOrderConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看交易订单详情")
    public CommonResult<TradeOrderViewResp> view(Long id) {
        return super.view(id, TradeOrderConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增交易订单")
    public CommonResult<TradeOrderCreateResp> create(@RequestBody TradeOrderCreateReq req) {
        return super.create(req, TradeOrderConvert.INSTANCE::convertToDTO, TradeOrderConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑交易订单")
    public CommonResult<Boolean> update(@RequestBody TradeOrderUpdateReq req) {
        return super.update(req, TradeOrderConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除交易订单")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TradeOrderExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出交易订单")
    public void export(TradeOrderPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "交易订单" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TradeOrderExp.class,
                    TradeOrderConvert.INSTANCE::convertToDTO, TradeOrderConvert.INSTANCE::convertToExp, response);
    }

}