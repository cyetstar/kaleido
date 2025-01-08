package cc.onelooker.kaleido.web.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.constant.Constants;

import cc.onelooker.kaleido.convert.SysResourceConvert;
import cc.onelooker.kaleido.dto.SysResourceDTO;
import cc.onelooker.kaleido.dto.req.SysResourceCreateReq;
import cc.onelooker.kaleido.dto.req.SysResourceInitReq;
import cc.onelooker.kaleido.dto.req.SysResourcePageReq;
import cc.onelooker.kaleido.dto.req.SysResourceUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysResourceCreateResp;
import cc.onelooker.kaleido.dto.resp.SysResourceListTypeResp;
import cc.onelooker.kaleido.dto.resp.SysResourcePageResp;
import cc.onelooker.kaleido.dto.resp.SysResourceViewResp;
import cc.onelooker.kaleido.service.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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

    @Override
    protected IBaseService<SysResourceDTO> getService() {
        return sysResourceService;
    }

    @GetMapping("page")
    @ApiOperation(value = "资源表分页查询", hidden = true)
    public CommonResult<PageResult<SysResourcePageResp>> page(SysResourcePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, SysResourceConvert.INSTANCE::convertToDTO,
                SysResourceConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "资源表详细信息", hidden = true)
    public CommonResult<SysResourceViewResp> view(Long id) {
        return super.view(id, SysResourceConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增资源表", hidden = true)
    public CommonResult<SysResourceCreateResp> create(@RequestBody SysResourceCreateReq req) {
        return super.create(req, SysResourceConvert.INSTANCE::convertToDTO,
                SysResourceConvert.INSTANCE::convertToCreateResp);
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
        Map<String, List<SysResourceListTypeResp>> result = sysResourceDTOList.stream()
                .map(s -> SysResourceConvert.INSTANCE.convertToListTypeResp(s))
                .sorted(Comparator.comparing(SysResourceListTypeResp::getType))
                .collect(Collectors.groupingBy(SysResourceListTypeResp::getType));
        TreeMap<String, List<SysResourceListTypeResp>> treeResult = Maps.newTreeMap();
        treeResult.putAll(result);
        return CommonResult.success(treeResult);
    }

    @GetMapping("listTypeByRoleId")
    @ApiOperation(value = "按类型分组查询资源")
    public CommonResult<Map<String, List<SysResourceListTypeResp>>> listTypeByRoleId(Long roleId) {
        List<SysResourceDTO> sysResourceDTOList = sysResourceService.listByRoleId(roleId);
        Map<String, List<SysResourceListTypeResp>> result = sysResourceDTOList.stream()
                .map(s -> SysResourceConvert.INSTANCE.convertToListTypeResp(s))
                .collect(Collectors.groupingBy(SysResourceListTypeResp::getType));
        return CommonResult.success(result);
    }
}