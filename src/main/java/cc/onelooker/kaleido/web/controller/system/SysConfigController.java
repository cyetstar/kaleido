package cc.onelooker.kaleido.web.controller.system;

import cc.onelooker.kaleido.convert.system.SysConfigConvert;
import cc.onelooker.kaleido.dto.system.SysConfigDTO;
import cc.onelooker.kaleido.dto.system.req.SysConfigCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysConfigPageReq;
import cc.onelooker.kaleido.dto.system.req.SysConfigSaveReq;
import cc.onelooker.kaleido.dto.system.req.SysConfigUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysConfigCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysConfigfindByKeysResp;
import cc.onelooker.kaleido.dto.system.resp.SysConfigPageResp;
import cc.onelooker.kaleido.dto.system.resp.SysConfigViewResp;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.service.system.SysConfigService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cn.hutool.core.util.ReflectUtil;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.reflect.ReflectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

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
    private PlexApiService plexApiService;

    @Override
    protected IBaseService getService() {
        return sysConfigService;
    }

    @PostMapping("save")
    @ApiOperation(value = "保存系统配置表")
    public CommonResult<Boolean> save(@RequestBody SysConfigSaveReq req) {
        Field[] fields = ReflectUtil.getFields(req.getClass());
        List<SysConfigDTO> sysConfigDTOList = Lists.newArrayList();
        for (Field field : fields) {
            SysConfigDTO sysConfigDTO = new SysConfigDTO();
            sysConfigDTO.setConfigKey(field.getName());
            sysConfigDTO.setConfigValue((String) ReflectUtil.getFieldValue(req, field));
            sysConfigDTOList.add(sysConfigDTO);
        }
        sysConfigService.save(sysConfigDTOList);
        plexApiService.init();
        return CommonResult.success(true);
    }

    @PostMapping("findByKeys")
    @ApiOperation(value = "获取系统配置")
    public CommonResult<SysConfigfindByKeysResp> findByKeys(@RequestBody String[] keys) {
        SysConfigfindByKeysResp resp = new SysConfigfindByKeysResp();
        for (String key : keys) {
            ReflectionUtils.setProperty(resp, key, ConfigUtils.getSysConfig(key, StringUtils.EMPTY));
        }
        return CommonResult.success(resp);
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