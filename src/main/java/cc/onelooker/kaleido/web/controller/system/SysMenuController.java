package cc.onelooker.kaleido.web.controller.system;

import cc.onelooker.kaleido.utils.CurrentUserUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.ExportColumn;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import com.zjjcnt.common.util.constant.Constants;
import cc.onelooker.kaleido.convert.system.SysMenuConvert;
import cc.onelooker.kaleido.dto.system.SysMenuDTO;
import cc.onelooker.kaleido.dto.system.SysResourceDTO;
import cc.onelooker.kaleido.dto.system.exp.SysMenuExp;
import cc.onelooker.kaleido.dto.system.req.SysMenuCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysMenuInitReq;
import cc.onelooker.kaleido.dto.system.req.SysMenuPageReq;
import cc.onelooker.kaleido.dto.system.req.SysMenuUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysMenuCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysMenuPageResp;
import cc.onelooker.kaleido.dto.system.resp.SysMenuTreeResp;
import cc.onelooker.kaleido.dto.system.resp.SysMenuViewResp;
import cc.onelooker.kaleido.service.system.SysMenuService;
import cc.onelooker.kaleido.service.system.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单表前端控制器
 *
 * @author xiadawei
 * @date 2022-11-13 01:12:24
 */

@Api(tags = "菜单表")
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController extends AbstractCrudController<SysMenuDTO> {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysResourceService sysResourceService;

    @Override
    protected IBaseService getService() {
        return sysMenuService;
    }

    @GetMapping("page")
    @ApiOperation(value = "菜单表分页查询")
    public CommonResult<PageResult<SysMenuPageResp>> page(SysMenuPageReq req, PageParam pageParam) {
        pageParam.setOrderBy("ASC:order_num,parent_id");
        return super.page(req, pageParam, SysMenuConvert.INSTANCE::convertToDTO, SysMenuConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "菜单表详细信息")
    public CommonResult<SysMenuViewResp> view(Long id) {
        return super.view(id, SysMenuConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增菜单表")
    public CommonResult<SysMenuCreateResp> create(@RequestBody SysMenuCreateReq req) {
        return super.create(req, SysMenuConvert.INSTANCE::convertToDTO, SysMenuConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑菜单表")
    public CommonResult<Boolean> update(@RequestBody SysMenuUpdateReq req) {
        return super.update(req, SysMenuConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除菜单表")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(SysMenuExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出数据")
    public void export(SysMenuPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "菜单表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, SysMenuExp.class,
                SysMenuConvert.INSTANCE::convertToDTO, SysMenuConvert.INSTANCE::convertToExp, response);
    }

    @PostMapping("init")
    @ApiOperation(value = "初始化菜单")
    public CommonResult<Boolean> init(@RequestBody List<SysMenuInitReq> reqList) {
        List<SysMenuDTO> sysMenuDTOList = convertToDTO(reqList,null);
        sysMenuService.init(sysMenuDTOList);
        return CommonResult.success(true);
    }

    @ApiOperation(value = "获取前端所需菜单")
    @GetMapping(value = "/tree")
    public CommonResult<Map<String, List<SysMenuTreeResp>>> tree(@RequestParam(required = false) String apps) {
        if (StringUtils.isNotEmpty(apps)) {
            String[] appArr = StringUtils.split(apps, Constants.COMMA);
            Map<String, List<SysMenuTreeResp>> result = Maps.newHashMap();
            for (String app : appArr) {
                List<SysMenuDTO> sysMenuDTOList = sysMenuService.listByApp(app);
                List<SysMenuTreeResp> respList = genSysMenuTreeRespList(sysMenuDTOList);
                result.put(app, respList);
            }
            return CommonResult.success(result);
        } else {
            List<SysMenuDTO> sysMenuDTOList = sysMenuService.list(null);
            List<SysMenuTreeResp> respList = genSysMenuTreeRespList(sysMenuDTOList);
            Map<String, List<SysMenuTreeResp>> result = ImmutableMap.of(StringUtils.EMPTY, respList);
            return CommonResult.success(result);
        }
    }

    private List<SysMenuTreeResp> genSysMenuTreeRespList(List<SysMenuDTO> sysMenuDTOList) {
        List<SysResourceDTO> sysResourceDTOList = sysResourceService.listByUserId(CurrentUserUtils.getUserId());
        List<String> permissionList = sysResourceDTOList.stream().map(s -> s.getCode()).collect(Collectors.toList());
        sysMenuDTOList = sysMenuDTOList.stream()
                .filter(s -> StringUtils.equals(s.getIsHidden(), Constants.NO)
                        && (StringUtils.isEmpty(s.getPermission()) || permissionList.contains(s.getPermission())))
                .collect(Collectors.toList());
        List<SysMenuTreeResp> respList = Lists.newArrayList();
        for (SysMenuDTO dto : sysMenuDTOList) {
            SysMenuTreeResp resp = SysMenuConvert.INSTANCE.convertToTreeResp(dto);
            respList.add(resp);
        }
        respList = buildTree(respList);
        //过滤无子节点的数据
        respList = respList.stream().filter(s -> CollectionUtils.isNotEmpty(s.getChildren())).collect(Collectors.toList());
        return respList;
    }

    /**
     * 将列表转成tree的结构
     *
     * @param respList
     * @return
     */
    public List<SysMenuTreeResp> buildTree(List<SysMenuTreeResp> respList) {
        List<SysMenuTreeResp> trees = new ArrayList<>();
        Map<Long, List<SysMenuTreeResp>> map = new HashMap<>();

        for (SysMenuTreeResp resp : respList) {
            if (resp.getParentId() == null || resp.getParentId() == 0L) {
                trees.add(resp);
            } else {
                map.putIfAbsent(resp.getParentId(), new ArrayList<>());
                List<SysMenuTreeResp> children = map.get(resp.getParentId());
                children.add(resp);
            }
        }
        for (SysMenuTreeResp resp : respList) {
            List<SysMenuTreeResp> children = map.getOrDefault(resp.getId(), new ArrayList<>());
            resp.setChildren(children);
        }
        return trees;
    }

    private List<SysMenuDTO> convertToDTO(List<SysMenuInitReq> reqList,String app) {
        List<SysMenuDTO> dtoList = Lists.newArrayList();
        for (SysMenuInitReq req : reqList) {
            SysMenuDTO dto = SysMenuConvert.INSTANCE.convertToDTO(req);
            dto.setApp(StringUtils.isBlank(app) ? req.getName() : app);
            if (req.getChildren() != null) {
                dto.setChildren(convertToDTO(req.getChildren(),dto.getApp()));
            }
            dtoList.add(dto);
        }
        return dtoList;

    }

}