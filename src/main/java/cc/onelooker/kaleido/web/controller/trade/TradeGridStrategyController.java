package cc.onelooker.kaleido.web.controller.trade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.trade.TradeGridStrategyService;
import cc.onelooker.kaleido.dto.trade.TradeGridStrategyDTO;
import cc.onelooker.kaleido.convert.trade.TradeGridStrategyConvert;
import cc.onelooker.kaleido.dto.trade.req.*;
import cc.onelooker.kaleido.dto.trade.resp.*;
import cc.onelooker.kaleido.exp.trade.TradeGridStrategyExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import com.zjjcnt.common.core.annotation.Dict;
import java.math.BigDecimal;
import java.lang.Integer;


/**
* 网格策略前端控制器
*
* @author cyetstar
* @date 2023-07-05 23:02:49
*/

@Api(tags = "网格策略")
@RestController
@RequestMapping("/tradeGridStrategy")
public class TradeGridStrategyController extends AbstractCrudController<TradeGridStrategyDTO>{

    @Autowired
    private TradeGridStrategyService tradeGridStrategyService;

    @Override
    protected IBaseService getService() {
        return tradeGridStrategyService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询网格策略")
    public CommonResult<PageResult<TradeGridStrategyPageResp>> page(TradeGridStrategyPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TradeGridStrategyConvert.INSTANCE::convertToDTO, TradeGridStrategyConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看网格策略详情")
    public CommonResult<TradeGridStrategyViewResp> view(Long id) {
        return super.view(id, TradeGridStrategyConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增网格策略")
    public CommonResult<TradeGridStrategyCreateResp> create(@RequestBody TradeGridStrategyCreateReq req) {
        return super.create(req, TradeGridStrategyConvert.INSTANCE::convertToDTO, TradeGridStrategyConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑网格策略")
    public CommonResult<Boolean> update(@RequestBody TradeGridStrategyUpdateReq req) {
        return super.update(req, TradeGridStrategyConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除网格策略")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TradeGridStrategyExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出网格策略")
    public void export(TradeGridStrategyPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "网格策略" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TradeGridStrategyExp.class,
                    TradeGridStrategyConvert.INSTANCE::convertToDTO, TradeGridStrategyConvert.INSTANCE::convertToExp, response);
    }

}