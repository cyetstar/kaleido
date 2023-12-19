package cc.onelooker.kaleido.web.controller.trade;

import cc.onelooker.kaleido.convert.trade.TradeGridConvert;
import cc.onelooker.kaleido.dto.trade.TradeGridDTO;
import cc.onelooker.kaleido.dto.trade.req.TradeGridCreateReq;
import cc.onelooker.kaleido.dto.trade.req.TradeGridPageReq;
import cc.onelooker.kaleido.dto.trade.req.TradeGridUpdateReq;
import cc.onelooker.kaleido.dto.trade.resp.TradeGridCreateResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeGridListByStrategyIdResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeGridPageResp;
import cc.onelooker.kaleido.dto.trade.resp.TradeGridViewResp;
import cc.onelooker.kaleido.exp.trade.TradeGridExp;
import cc.onelooker.kaleido.service.trade.TradeGridService;
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
* 交易网格前端控制器
*
* @author cyetstar
* @date 2023-07-05 23:02:49
*/

@Api(tags = "交易网格")
@RestController
@RequestMapping("/tradeGrid")
public class TradeGridController extends AbstractCrudController<TradeGridDTO>{

    @Autowired
    private TradeGridService tradeGridService;

    @Override
    protected IBaseService getService() {
        return tradeGridService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询交易网格")
    public CommonResult<PageResult<TradeGridPageResp>> page(TradeGridPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TradeGridConvert.INSTANCE::convertToDTO, TradeGridConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看交易网格详情")
    public CommonResult<TradeGridViewResp> view(Long id) {
        return super.view(id, TradeGridConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增交易网格")
    public CommonResult<TradeGridCreateResp> create(@RequestBody TradeGridCreateReq req) {
        return super.create(req, TradeGridConvert.INSTANCE::convertToDTO, TradeGridConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑交易网格")
    public CommonResult<Boolean> update(@RequestBody TradeGridUpdateReq req) {
        return super.update(req, TradeGridConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除交易网格")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TradeGridExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出交易网格")
    public void export(TradeGridPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "交易网格" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TradeGridExp.class,
                    TradeGridConvert.INSTANCE::convertToDTO, TradeGridConvert.INSTANCE::convertToExp, response);
    }

    @GetMapping(value = "/listByStrategyId")
    @ApiOperation(value = "获取策略网格列表")
    public CommonResult<List<TradeGridListByStrategyIdResp>> listByStrategyId(Long strategyId) {
        List<TradeGridDTO> tradeGridDTOList = tradeGridService.listByStrategyId(strategyId);
        List<TradeGridListByStrategyIdResp> respList = TradeGridConvert.INSTANCE.convertToListByStrategyIdResp(tradeGridDTOList);
        return CommonResult.success(respList);
    }

}