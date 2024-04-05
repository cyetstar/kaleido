package cc.onelooker.kaleido.web.controller.comic;

import cc.onelooker.kaleido.convert.comic.ComicAuthorConvert;
import cc.onelooker.kaleido.dto.comic.ComicAuthorDTO;
import cc.onelooker.kaleido.dto.comic.req.ComicAuthorCreateReq;
import cc.onelooker.kaleido.dto.comic.req.ComicAuthorPageReq;
import cc.onelooker.kaleido.dto.comic.req.ComicAuthorUpdateReq;
import cc.onelooker.kaleido.dto.comic.resp.ComicAuthorCreateResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicAuthorPageResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicAuthorViewResp;
import cc.onelooker.kaleido.service.comic.ComicAuthorService;
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
@RequestMapping("/comicAuthor")
public class ComicAuthorController extends AbstractCrudController<ComicAuthorDTO>{

    @Autowired
    private ComicAuthorService comicAuthorService;

    @Override
    protected IBaseService getService() {
        return comicAuthorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询漫画作者")
    public CommonResult<PageResult<ComicAuthorPageResp>> page(ComicAuthorPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, ComicAuthorConvert.INSTANCE::convertToDTO, ComicAuthorConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看漫画作者详情")
    public CommonResult<ComicAuthorViewResp> view(String id) {
        return super.view(id, ComicAuthorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增漫画作者")
    public CommonResult<ComicAuthorCreateResp> create(@RequestBody ComicAuthorCreateReq req) {
        return super.create(req, ComicAuthorConvert.INSTANCE::convertToDTO, ComicAuthorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑漫画作者")
    public CommonResult<Boolean> update(@RequestBody ComicAuthorUpdateReq req) {
        return super.update(req, ComicAuthorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除漫画作者")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }


}