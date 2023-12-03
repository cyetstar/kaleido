package cc.onelooker.kaleido.web.controller.trade;

import cc.onelooker.kaleido.third.mexc.MexcApiService;
import cc.onelooker.kaleido.third.mexc.resp.DefaultSymbolsResp;
import cc.onelooker.kaleido.third.mexc.resp.ExchangeInfoResp;
import cc.onelooker.kaleido.third.mexc.resp.TickerPriceResp;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import com.zjjcnt.common.util.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.trade.TradeSymbolService;
import cc.onelooker.kaleido.dto.trade.TradeSymbolDTO;
import cc.onelooker.kaleido.convert.trade.TradeSymbolConvert;
import cc.onelooker.kaleido.dto.trade.req.*;
import cc.onelooker.kaleido.dto.trade.resp.*;
import cc.onelooker.kaleido.exp.trade.TradeSymbolExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.String;
import java.lang.Long;

/**
 * 交易商品前端控制器
 *
 * @author cyetstar
 * @date 2023-07-06 11:56:08
 */

@Api(tags = "交易商品")
@RestController
@RequestMapping("/tradeSymbol")
public class TradeSymbolController extends AbstractCrudController<TradeSymbolDTO> {

    @Autowired
    private TradeSymbolService tradeSymbolService;

    @Autowired
    private MexcApiService mexcApiService;

    @Override
    protected IBaseService getService() {
        return tradeSymbolService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询交易商品")
    public CommonResult<PageResult<TradeSymbolPageResp>> page(TradeSymbolPageReq req, PageParam pageParam) {
        pageParam.setOrderBy("DESC:sfsc");
        return super.page(req, pageParam, TradeSymbolConvert.INSTANCE::convertToDTO, TradeSymbolConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看交易商品详情")
    public CommonResult<TradeSymbolViewResp> view(Long id) {
        return super.view(id, TradeSymbolConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增交易商品")
    public CommonResult<TradeSymbolCreateResp> create(@RequestBody TradeSymbolCreateReq req) {
        return super.create(req, TradeSymbolConvert.INSTANCE::convertToDTO, TradeSymbolConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑交易商品")
    public CommonResult<Boolean> update(@RequestBody TradeSymbolUpdateReq req) {
        return super.update(req, TradeSymbolConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除交易商品")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TradeSymbolExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出交易商品")
    public void export(TradeSymbolPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "交易商品" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TradeSymbolExp.class, TradeSymbolConvert.INSTANCE::convertToDTO, TradeSymbolConvert.INSTANCE::convertToExp, response);
    }

    @PostMapping("fetch")
    @ApiOperation(value = "获取交易商品")
    public CommonResult<Boolean> fetch() {
        DefaultSymbolsResp resp = mexcApiService.defaultSymbols();
        tradeSymbolService.batchSave(resp.getData(), KaleidoConstants.JYS_MEXC);
        return CommonResult.success(true);
    }

    @PostMapping("fetchDetail")
    @ApiOperation(value = "获取交易商品详细信息")
    public CommonResult<Boolean> fetchDetail(@RequestBody TradeSymbolFetchDetailReq req) {
        TradeSymbolDTO tradeSymbolDTO = tradeSymbolService.findById(req.getId());
        ExchangeInfoResp exchangeInfo = mexcApiService.exchangeInfo(tradeSymbolDTO.getSpmc());
        ExchangeInfoResp.Symbol symbol = CollectionUtils.get(exchangeInfo.getSymbols(), 0);
        tradeSymbolDTO.setJyb(symbol.getBaseAsset());
        tradeSymbolDTO.setJybjd(symbol.getBaseAssetPrecision());
        tradeSymbolDTO.setJybsxfjd(symbol.getBaseCommissionPrecision());
        tradeSymbolDTO.setJjb(symbol.getQuoteAsset());
        tradeSymbolDTO.setJjbjgjd(symbol.getQuotePrecision());
        tradeSymbolDTO.setJjbsxfjd(symbol.getQuoteCommissionPrecision());
        tradeSymbolDTO.setJjbzcjd(symbol.getQuoteAssetPrecision());
        tradeSymbolDTO.setZcddlx(StringUtils.join(symbol.getOrderTypes(), Constants.COMMA));
        tradeSymbolDTO.setSfyxsjwt(BooleanUtils.toString(symbol.getQuoteOrderQtyMarketAllowed(), Constants.YES, Constants.NO, null));
        tradeSymbolDTO.setSfyxapixhjy(BooleanUtils.toString(symbol.getIsSpotTradingAllowed(), Constants.YES, Constants.NO, null));
        tradeSymbolDTO.setSfyxapiggjy(BooleanUtils.toString(symbol.getIsMarginTradingAllowed(), Constants.YES, Constants.NO, null));
        tradeSymbolDTO.setQx(StringUtils.join(symbol.getPermissions(), Constants.COMMA));
        tradeSymbolDTO.setZdwtsl(symbol.getMaxQuoteAmount());
        tradeSymbolDTO.setZxxdje(symbol.getQuoteAmountPrecision());
        tradeSymbolDTO.setZxxdsl(symbol.getBaseSizePrecision());
        tradeSymbolDTO.setMarkersxf(symbol.getMakerCommission());
        tradeSymbolDTO.setTakersxf(symbol.getTakerCommission());
        tradeSymbolService.update(tradeSymbolDTO);
        return CommonResult.success(true);
    }

    @PostMapping("like")
    @ApiOperation(value = "获取交易商品")
    public CommonResult<Boolean> like(@RequestBody TradeSymbolLikeReq req) {
        tradeSymbolService.like(req.getId(), req.getSfsc());
        return CommonResult.success(true);
    }

    @GetMapping("getPrice")
    @ApiOperation(value = "获取交易商品价格")
    public CommonResult<String> getPrice(Long id) {
        TradeSymbolDTO tradeSymbolDTO = tradeSymbolService.findById(id);
        TickerPriceResp tickerPrice = mexcApiService.tickerPrice(tradeSymbolDTO.getSpmc());
        return CommonResult.success(tickerPrice.getPrice());
    }

}