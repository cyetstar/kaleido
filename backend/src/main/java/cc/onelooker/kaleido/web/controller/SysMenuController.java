package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.SysMenuConvert;
import cc.onelooker.kaleido.dto.SysMenuDTO;
import cc.onelooker.kaleido.dto.SysResourceDTO;
import cc.onelooker.kaleido.dto.req.*;
import cc.onelooker.kaleido.dto.resp.SysMenuCreateResp;
import cc.onelooker.kaleido.dto.resp.SysMenuPageResp;
import cc.onelooker.kaleido.dto.resp.SysMenuTreeResp;
import cc.onelooker.kaleido.dto.resp.SysMenuViewResp;
import cc.onelooker.kaleido.service.SysMenuService;
import cc.onelooker.kaleido.service.SysResourceService;
import cc.onelooker.kaleido.utils.CurrentUserUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单表前端控制器
 *
 * @author cyetstar
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
    protected IBaseService<SysMenuDTO> getService() {
        return sysMenuService;
    }

    @GetMapping("page")
    @ApiOperation(value = "菜单表分页查询")
    public CommonResult<PageResult<SysMenuPageResp>> page(SysMenuPageReq req, PageParam pageParam) {
        pageParam.setOrderBy("ASC:order_num,parent_id");
        return super.page(req, pageParam, SysMenuConvert.INSTANCE::convertToDTO,
                SysMenuConvert.INSTANCE::convertToPageResp);
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
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @PostMapping("init")
    @ApiOperation(value = "初始化菜单")
    public CommonResult<Boolean> init(@RequestBody List<SysMenuInitReq> reqList) {
        List<SysMenuDTO> sysMenuDTOList = convertToDTO(reqList, null);
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
            Map<String, List<SysMenuTreeResp>> result = ImmutableMap.of("default", respList);
            return CommonResult.success(result);
        }
    }

    @ApiOperation(value = "获取前端所需菜单")
    @GetMapping(value = "/tree2")
    public CommonResult<List<SysMenuTreeResp>> tree2() {
        List<SysMenuDTO> sysMenuDTOList = sysMenuService.list(null);
        sysMenuDTOList = sysMenuDTOList.stream().sorted(Comparator.comparingInt(SysMenuDTO::getOrderNum))
                .collect(Collectors.toList());
        List<SysMenuTreeResp> respList = genSysMenuTreeRespList(sysMenuDTOList);
        return CommonResult.success(respList);
    }

    @ApiOperation(value = "更新菜单是否隐藏")
    @PostMapping("updateHidden")
    public CommonResult<Boolean> updateHidden(@RequestBody SysMenuUpdateHiddenReq req) {
        sysMenuService.updateIsHidden(req.getHidden(), req.getId());
        return CommonResult.success(true);
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
        // 过滤无子节点的数据
        respList = respList.stream().filter(s -> CollectionUtils.isNotEmpty(s.getChildren()))
                .collect(Collectors.toList());
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

    private List<SysMenuDTO> convertToDTO(List<SysMenuInitReq> reqList, String app) {
        List<SysMenuDTO> dtoList = Lists.newArrayList();
        for (SysMenuInitReq req : reqList) {
            SysMenuDTO dto = SysMenuConvert.INSTANCE.convertToDTO(req);
            if (req.getChildren() != null) {
                dto.setChildren(convertToDTO(req.getChildren(), dto.getApp()));
            }
            dtoList.add(dto);
        }
        return dtoList;

    }

}