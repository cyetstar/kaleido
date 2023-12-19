package cc.onelooker.kaleido.web.controller.trade;

import cc.onelooker.kaleido.convert.trade.TradeOrderConvert;
import cc.onelooker.kaleido.dto.trade.TradeOrderDTO;
import cc.onelooker.kaleido.dto.trade.TradeSymbolDTO;
import cc.onelooker.kaleido.dto.trade.req.TradeOrderCreateReq;
import cc.onelooker.kaleido.dto.trade.req.TradeOrderOrderReq;
import cc.onelooker.kaleido.dto.trade.req.TradeOrderPageReq;
import cc.onelooker.kaleido.dto.trade.req.TradeOrderUpdateReq;
import cc.onelooker.kaleido.dto.trade.resp.TradeOrderCreateResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeOrderPageByGridIdResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeOrderPageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeOrderViewResp;
import cc.onelooker.kaleido.exp.trade.TradeOrderExp;
import cc.onelooker.kaleido.service.trade.TradeOrderService;
import cc.onelooker.kaleido.service.trade.TradeSymbolService;
import cc.onelooker.kaleido.third.mexc.MexcApiService;
import cc.onelooker.kaleido.third.mexc.req.OrderReq;
import cc.onelooker.kaleido.third.mexc.resp.OrderResp;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.ExportColumn;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 交易订单前端控制器
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 */

@Api(tags = "交易订单")
@RestController
@RequestMapping("/tradeOrder")
public class TradeOrderController extends AbstractCrudController<TradeOrderDTO> {

    @Autowired
    private TradeOrderService tradeOrderService;

    @Autowired
    private TradeSymbolService tradeSymbolService;

    @Autowired
    private MexcApiService mexcApiService;

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
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
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
        super.export(req, columns, pageParam, filename, TradeOrderExp.class, TradeOrderConvert.INSTANCE::convertToDTO, TradeOrderConvert.INSTANCE::convertToExp, response);
    }

    @GetMapping(value = "/pageByGridId")
    @ApiOperation(value = "获取交易订单列表")
    public CommonResult<PageResult<TradeOrderPageByGridIdResp>> pageByGridId(Long gridId, PageParam pageParam) {
        PageResult<TradeOrderDTO> pageResult = tradeOrderService.pageByGridId(gridId, pageParam.toPage());
        PageResult<TradeOrderPageByGridIdResp> respPageResult = PageResult.convert(pageResult, TradeOrderConvert.INSTANCE::convertToPageByGridIdResp);
        return CommonResult.success(respPageResult);
    }

    @PostMapping("order")
    @ApiOperation(value = "发送交易订单")
    public CommonResult<OrderResp> order(@RequestBody TradeOrderOrderReq req) {
        TradeOrderDTO tradeOrderDTO = tradeOrderService.findById(req.getId());
        TradeSymbolDTO tradeSymbolDTO = tradeSymbolService.findById(tradeOrderDTO.getSymbolId());
        OrderReq orderReq = new OrderReq();
        orderReq.setNewClientOrderId(String.valueOf(tradeOrderDTO.getId()));
        orderReq.setType(tradeOrderDTO.getDdlx());
        orderReq.setSide(tradeOrderDTO.getDdfx());
        orderReq.setQuantity(tradeOrderDTO.getYssl());
        orderReq.setPrice(tradeOrderDTO.getJg());
        orderReq.setQuoteOrderQty(tradeOrderDTO.getYsjyje());
        orderReq.setSymbol(tradeSymbolDTO.getSpmc());
        OrderResp orderResp = mexcApiService.order(orderReq);
        return CommonResult.success(orderResp);
    }

}