package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.ComicSeriesAuthorConvert;
import cc.onelooker.kaleido.dto.ComicSeriesAuthorDTO;
import cc.onelooker.kaleido.dto.req.ComicSeriesAuthorCreateReq;
import cc.onelooker.kaleido.dto.req.ComicSeriesAuthorPageReq;
import cc.onelooker.kaleido.dto.req.ComicSeriesAuthorUpdateReq;
import cc.onelooker.kaleido.dto.resp.ComicSeriesAuthorCreateResp;
import cc.onelooker.kaleido.dto.resp.ComicSeriesAuthorPageResp;
import cc.onelooker.kaleido.dto.resp.ComicSeriesAuthorViewResp;
import cc.onelooker.kaleido.service.ComicSeriesAuthorService;
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
@RequestMapping("/comicSeriesAuthor")
public class ComicSeriesAuthorController extends AbstractCrudController<ComicSeriesAuthorDTO>{

    @Autowired
    private ComicSeriesAuthorService comicSeriesAuthorService;

    @Override
    protected IBaseService getService() {
        return comicSeriesAuthorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询漫画书籍作者关联表")
    public CommonResult<PageResult<ComicSeriesAuthorPageResp>> page(ComicSeriesAuthorPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, ComicSeriesAuthorConvert.INSTANCE::convertToDTO, ComicSeriesAuthorConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看漫画书籍作者关联表详情")
    public CommonResult<ComicSeriesAuthorViewResp> view(String id) {
        return super.view(id, ComicSeriesAuthorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增漫画书籍作者关联表")
    public CommonResult<ComicSeriesAuthorCreateResp> create(@RequestBody ComicSeriesAuthorCreateReq req) {
        return super.create(req, ComicSeriesAuthorConvert.INSTANCE::convertToDTO, ComicSeriesAuthorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑漫画书籍作者关联表")
    public CommonResult<Boolean> update(@RequestBody ComicSeriesAuthorUpdateReq req) {
        return super.update(req, ComicSeriesAuthorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除漫画书籍作者关联表")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }


}