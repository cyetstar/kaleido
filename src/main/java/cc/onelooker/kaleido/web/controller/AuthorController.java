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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* 漫画作者前端控制器
*
* @author cyetstar
* @date 2024-03-12 17:47:50
*/

@Api(tags = "漫画作者")
@RestController
@RequestMapping("/author")
public class AuthorController extends AbstractCrudController<AuthorDTO>{

    @Autowired
    private AuthorService authorService;

    @Override
    protected IBaseService getService() {
        return authorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询漫画作者")
    public CommonResult<PageResult<AuthorPageResp>> page(AuthorPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, AuthorConvert.INSTANCE::convertToDTO, AuthorConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看漫画作者详情")
    public CommonResult<AuthorViewResp> view(String id) {
        return super.view(id, AuthorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增漫画作者")
    public CommonResult<AuthorCreateResp> create(@RequestBody AuthorCreateReq req) {
        return super.create(req, AuthorConvert.INSTANCE::convertToDTO, AuthorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑漫画作者")
    public CommonResult<Boolean> update(@RequestBody AuthorUpdateReq req) {
        return super.update(req, AuthorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除漫画作者")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }


}