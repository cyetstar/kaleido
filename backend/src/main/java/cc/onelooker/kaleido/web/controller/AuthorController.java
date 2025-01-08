package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.AuthorConvert;
import cc.onelooker.kaleido.dto.AuthorDTO;
import cc.onelooker.kaleido.dto.req.AuthorCreateReq;
import cc.onelooker.kaleido.dto.req.AuthorPageReq;
import cc.onelooker.kaleido.dto.req.AuthorUpdateReq;
import cc.onelooker.kaleido.dto.resp.AuthorCreateResp;
import cc.onelooker.kaleido.dto.resp.AuthorPageResp;
import cc.onelooker.kaleido.dto.resp.AuthorViewResp;
import cc.onelooker.kaleido.service.AuthorService;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 作者前端控制器
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */

@Api(tags = "作者")
@RestController
@RequestMapping("/author")
public class AuthorController extends AbstractCrudController<AuthorDTO> {

    @Autowired
    private AuthorService authorService;

    @Override
    protected IBaseService<AuthorDTO> getService() {
        return authorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询作者")
    public CommonResult<PageResult<AuthorPageResp>> page(AuthorPageReq req, PageParam pageParam) {
        AuthorDTO dto = AuthorConvert.INSTANCE.convertToDTO(req);
        if (StringUtils.isNotEmpty(req.getIds())) {
            dto.setIdList(Arrays.asList(StringUtils.split(req.getIds(), Constants.COMMA)));
        }
        PageResult<AuthorDTO> dtoPageResult = authorService.page(dto, pageParam.toPage());
        PageResult<AuthorPageResp> pageResult = PageResult.convert(dtoPageResult,
                AuthorConvert.INSTANCE::convertToPageResp);
        return CommonResult.success(pageResult);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看作者详情")
    public CommonResult<AuthorViewResp> view(String id) {
        return super.view(id, AuthorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增作者")
    public CommonResult<AuthorCreateResp> create(@RequestBody AuthorCreateReq req) {
        return super.create(req, AuthorConvert.INSTANCE::convertToDTO, AuthorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑作者")
    public CommonResult<Boolean> update(@RequestBody AuthorUpdateReq req) {
        return super.update(req, AuthorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除作者")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

}