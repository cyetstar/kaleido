package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.ObjectConvert;
import cc.onelooker.kaleido.convert.SysDictConvert;
import cc.onelooker.kaleido.dto.SysDictDTO;
import cc.onelooker.kaleido.dto.req.SysDictCreateBatchReq;
import cc.onelooker.kaleido.dto.req.SysDictCreateReq;
import cc.onelooker.kaleido.dto.req.SysDictPageReq;
import cc.onelooker.kaleido.dto.req.SysDictUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysDictPageResp;
import cc.onelooker.kaleido.dto.resp.SysDictViewResp;
import cc.onelooker.kaleido.service.SysDictService;
import com.zjjcnt.common.core.annotation.CacheControl;
import com.zjjcnt.common.core.dict.Dictionary;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.domain.TextValue;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典表前端控制器
 *
 * @author cyetstar
 * @date 2022-11-19 23:17:28
 */

@Api(tags = "字典表")
@RestController
@RequestMapping("/sysDict")
public class SysDictController extends AbstractCrudController<SysDictDTO> {

    @Autowired
    private SysDictService sysDictService;

    @Override
    protected IBaseService getService() {
        return sysDictService;
    }

    @GetMapping("page")
    @ApiOperation(value = "字典表分页查询")
    public CommonResult<PageResult<SysDictPageResp>> page(SysDictPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, SysDictConvert.INSTANCE::convertToDTO, SysDictConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "字典表详细信息")
    public CommonResult<SysDictViewResp> view(Long id) {
        return super.view(id, SysDictConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增字典表")
    public CommonResult<Boolean> create(@RequestBody SysDictCreateReq req) {
        return super.create(req, SysDictConvert.INSTANCE::convertToDTO, ObjectConvert.INSTANCE::convertToBoolean);
    }

    @PostMapping("createBatch")
    @ApiOperation(value = "批量新增字典表")
    public CommonResult<Boolean> createBatch(@RequestBody SysDictCreateBatchReq req) {
        String content = req.getContent();
        String[] lineArr = StringUtils.split(content, '\n');
        for (int i = 0; i < lineArr.length; i++) {
            String line = lineArr[i];
            String[] itemArr = StringUtils.split(line);
            if (ArrayUtils.getLength(itemArr) == 2) {
                SysDictDTO sysDictDTO = new SysDictDTO();
                sysDictDTO.setDictType(req.getDictType());
                sysDictDTO.setValue(itemArr[0]);
                sysDictDTO.setLabel(itemArr[1]);
                sysDictDTO.setSort(i + 1);
                sysDictService.insert(sysDictDTO);
            }
        }
        return CommonResult.success(true);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑字典表")
    public CommonResult<Boolean> update(@RequestBody SysDictUpdateReq req) {
        return CommonResult.success(sysDictService.updateById(req));
    }

    @CacheControl(maxAge = 60)
    @ApiOperation(value = "根据类型获取字典列表")
    @GetMapping(value = "/listByDictType")
    public CommonResult<List<TextValue>> listByDictType(@RequestParam String dictType) {
        Map<String, String> codeMap = Dictionary.get(dictType);
        List<TextValue> list = codeMap.keySet().stream().map(s -> new TextValue(codeMap.get(s), s)).collect(Collectors.toList());
        return CommonResult.success(list);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除字典表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @ApiOperation(value = "查询所有")
    @GetMapping(value = "/listAll")
    public CommonResult<List<SysDictPageResp>> listAll(SysDictPageReq req) {
        return CommonResult.success(sysDictService.listAll(req));
    }

}