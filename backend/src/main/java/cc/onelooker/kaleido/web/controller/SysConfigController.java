package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.SysConfigConvert;
import cc.onelooker.kaleido.dto.SysConfigDTO;
import cc.onelooker.kaleido.dto.req.SysConfigCreateReq;
import cc.onelooker.kaleido.dto.req.SysConfigPageReq;
import cc.onelooker.kaleido.dto.req.SysConfigUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysConfigCreateResp;
import cc.onelooker.kaleido.dto.resp.SysConfigPageResp;
import cc.onelooker.kaleido.dto.resp.SysConfigViewResp;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.SysConfigService;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统配置表前端控制器
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */

@Api(tags = "系统配置表")
@RestController
@RequestMapping("/sysConfig")
public class SysConfigController extends AbstractCrudController<SysConfigDTO> {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private TmmApiService tmmApiService;

    @Override
    protected IBaseService getService() {
        return sysConfigService;
    }

    @PostMapping("save")
    @ApiOperation(value = "保存系统配置表")
    public CommonResult<Boolean> save(@RequestBody Map<String, Object> req) {
        List<SysConfigDTO> sysConfigDTOList = Lists.newArrayList();
        for (String key : req.keySet()) {
            sysConfigDTOList.add(new SysConfigDTO(key, key, MapUtils.getString(req, key)));
        }
        sysConfigService.save(sysConfigDTOList);
        sysConfigDTOList.stream().filter(s -> StringUtils.isNotEmpty(s.getConfigValue())
                && ConfigKey.doubanCookie.name().equals(s.getConfigKey())).findFirst().ifPresent(s -> {
            tmmApiService.setDoubanCookie(s.getConfigValue());
        });
        return CommonResult.success(true);
    }

    @PostMapping("findByKeys")
    @ApiOperation(value = "获取系统配置")
    public CommonResult<Map<String, Object>> findByKeys(@RequestBody String[] keys) {
        Map<String, Object> resultMap = Maps.newHashMap();
        for (String key : keys) {
            resultMap.put(key, ConfigUtils.getSysConfig(key, StringUtils.EMPTY));
        }
        return CommonResult.success(resultMap);
    }

    @GetMapping("page")
    @ApiOperation(value = "系统配置表分页查询")
    public CommonResult<PageResult<SysConfigPageResp>> page(SysConfigPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, SysConfigConvert.INSTANCE::convertToDTO, SysConfigConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "系统配置表详细信息")
    public CommonResult<SysConfigViewResp> view(Long id) {
        return super.view(id, SysConfigConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增系统配置表")
    public CommonResult<SysConfigCreateResp> create(@RequestBody SysConfigCreateReq req) {
        return super.create(req, SysConfigConvert.INSTANCE::convertToDTO, SysConfigConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑系统配置表")
    public CommonResult<Boolean> update(@RequestBody SysConfigUpdateReq req) {
        return super.update(req, SysConfigConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除系统配置表")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

}