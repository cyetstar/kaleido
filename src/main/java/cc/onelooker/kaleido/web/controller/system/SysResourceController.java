package cc.onelooker.kaleido.web.controller.system;

import com.google.common.collect.Maps;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.ExportColumn;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import com.zjjcnt.common.util.constant.Constants;
import cc.onelooker.kaleido.convert.system.SysResourceConvert;
import cc.onelooker.kaleido.dto.system.SysResourceDTO;
import cc.onelooker.kaleido.dto.system.SysRoleResourceDTO;
import cc.onelooker.kaleido.dto.system.exp.SysResourceExp;
import cc.onelooker.kaleido.dto.system.req.SysResourceCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysResourceInitReq;
import cc.onelooker.kaleido.dto.system.req.SysResourcePageReq;
import cc.onelooker.kaleido.dto.system.req.SysResourceUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysResourceCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysResourceListTypeResp;
import cc.onelooker.kaleido.dto.system.resp.SysResourcePageResp;
import cc.onelooker.kaleido.dto.system.resp.SysResourceViewResp;
import cc.onelooker.kaleido.service.system.SysResourceService;
import cc.onelooker.kaleido.service.system.SysRoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源表前端控制器
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */

@Api(tags = "资源表")
@RestController
@RequestMapping("/sysResource")
public class SysResourceController extends AbstractCrudController<SysResourceDTO> {

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysRoleResourceService sysRoleResourceService;

    @Override
    protected IBaseService getService() {
        return sysResourceService;
    }

    @GetMapping("page")
    @ApiOperation(value = "资源表分页查询", hidden = true)
    public CommonResult<PageResult<SysResourcePageResp>> page(SysResourcePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, SysResourceConvert.INSTANCE::convertToDTO, SysResourceConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "资源表详细信息", hidden = true)
    public CommonResult<SysResourceViewResp> view(Long id) {
        return super.view(id, SysResourceConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增资源表", hidden = true)
    public CommonResult<SysResourceCreateResp> create(@RequestBody SysResourceCreateReq req) {
        return super.create(req, SysResourceConvert.INSTANCE::convertToDTO, SysResourceConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑资源表", hidden = true)
    public CommonResult<Boolean> update(@RequestBody SysResourceUpdateReq req) {
        return super.update(req, SysResourceConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除资源表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(SysResourceExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出数据")
    public void export(SysResourcePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "资源表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, SysResourceExp.class, SysResourceConvert.INSTANCE::convertToDTO, SysResourceConvert.INSTANCE::convertToExp, response);
    }

    @PostMapping("init")
    @ApiOperation(value = "初始化资源")
    public CommonResult<Boolean> init(@RequestBody List<SysResourceInitReq> reqList) {
        List<SysResourceDTO> resourceDTOList = Lists.newArrayList();
        for (SysResourceInitReq req : reqList) {
            for (String action : req.getAction()) {
                SysResourceDTO dto = new SysResourceDTO();
                dto.setName(action);
                dto.setCode(req.getType() + Constants.COLON + action);
                dto.setType(req.getType());
                dto.setUrl(Constants.SLASH + req.getType() + Constants.SLASH + action);
                resourceDTOList.add(dto);
            }
        }
        sysResourceService.init(resourceDTOList);
        return CommonResult.success(true);
    }

    @GetMapping("listType")
    @ApiOperation(value = "按类型分组查询资源")
    public CommonResult<Map<String, List<SysResourceListTypeResp>>> listType() {
        List<SysResourceDTO> sysResourceDTOList = sysResourceService.list(null);
        Map<String, List<SysResourceListTypeResp>> result = sysResourceDTOList.stream().map(s -> SysResourceConvert.INSTANCE.convertToListTypeResp(s)).sorted(Comparator.comparing(SysResourceListTypeResp::getType)).collect(Collectors.groupingBy(SysResourceListTypeResp::getType));
        TreeMap<String, List<SysResourceListTypeResp>> treeResult = Maps.newTreeMap();
        treeResult.putAll(result);
        return CommonResult.success(treeResult);
    }

    @GetMapping("listTypeByRoleId")
    @ApiOperation(value = "按类型分组查询资源")
    public CommonResult<Map<String, List<SysResourceListTypeResp>>> listTypeByRoleId(Long roleId) {
        List<SysResourceDTO> sysResourceDTOList = sysResourceService.listByRoleId(roleId);
        Map<String, List<SysResourceListTypeResp>> result = sysResourceDTOList.stream().map(s -> SysResourceConvert.INSTANCE.convertToListTypeResp(s)).collect(Collectors.groupingBy(SysResourceListTypeResp::getType));
        return CommonResult.success(result);
    }
}