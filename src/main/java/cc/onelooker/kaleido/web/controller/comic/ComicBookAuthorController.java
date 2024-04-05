package cc.onelooker.kaleido.web.controller.comic;

import cc.onelooker.kaleido.convert.comic.ComicBookAuthorConvert;
import cc.onelooker.kaleido.dto.comic.ComicBookAuthorDTO;
import cc.onelooker.kaleido.dto.comic.req.ComicBookAuthorCreateReq;
import cc.onelooker.kaleido.dto.comic.req.ComicBookAuthorPageReq;
import cc.onelooker.kaleido.dto.comic.req.ComicBookAuthorUpdateReq;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookAuthorCreateResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookAuthorPageResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookAuthorViewResp;
import cc.onelooker.kaleido.service.comic.ComicBookAuthorService;
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
* 漫画书籍作者关联表前端控制器
*
* @author cyetstar
* @date 2024-03-12 17:47:50
*/

@Api(tags = "漫画书籍作者关联表")
@RestController
@RequestMapping("/comicBookAuthor")
public class ComicBookAuthorController extends AbstractCrudController<ComicBookAuthorDTO>{

    @Autowired
    private ComicBookAuthorService comicBookAuthorService;

    @Override
    protected IBaseService getService() {
        return comicBookAuthorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询漫画书籍作者关联表")
    public CommonResult<PageResult<ComicBookAuthorPageResp>> page(ComicBookAuthorPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, ComicBookAuthorConvert.INSTANCE::convertToDTO, ComicBookAuthorConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看漫画书籍作者关联表详情")
    public CommonResult<ComicBookAuthorViewResp> view(String id) {
        return super.view(id, ComicBookAuthorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增漫画书籍作者关联表")
    public CommonResult<ComicBookAuthorCreateResp> create(@RequestBody ComicBookAuthorCreateReq req) {
        return super.create(req, ComicBookAuthorConvert.INSTANCE::convertToDTO, ComicBookAuthorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑漫画书籍作者关联表")
    public CommonResult<Boolean> update(@RequestBody ComicBookAuthorUpdateReq req) {
        return super.update(req, ComicBookAuthorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除漫画书籍作者关联表")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }


}