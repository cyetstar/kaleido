package cc.onelooker.kaleido.web.controller.comic;

import cc.onelooker.kaleido.convert.comic.ComicBookConvert;
import cc.onelooker.kaleido.dto.comic.ComicAuthorDTO;
import cc.onelooker.kaleido.dto.comic.ComicBookDTO;
import cc.onelooker.kaleido.dto.comic.req.ComicBookCreateReq;
import cc.onelooker.kaleido.dto.comic.req.ComicBookPageReq;
import cc.onelooker.kaleido.dto.comic.req.ComicBookUpdateReq;
import cc.onelooker.kaleido.dto.comic.req.ComicBookUploadCoverReq;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookCreateResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookListPageResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookPageResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicBookViewResp;
import cc.onelooker.kaleido.service.comic.ComicAuthorService;
import cc.onelooker.kaleido.service.comic.ComicBookService;
import cc.onelooker.kaleido.third.komga.KomgaApiService;
import cc.onelooker.kaleido.third.komga.Page;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cn.hutool.core.codec.Base64;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 漫画书籍前端控制器
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */

@Api(tags = "漫画书籍")
@RestController
@RequestMapping("/comicBook")
public class ComicBookController extends AbstractCrudController<ComicBookDTO> {

    @Autowired
    private ComicBookService comicBookService;

    @Autowired
    private ComicAuthorService comicAuthorService;

    @Autowired
    private KomgaApiService komgaApiService;

    @Override
    protected IBaseService getService() {
        return comicBookService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询漫画书籍")
    public CommonResult<PageResult<ComicBookPageResp>> page(ComicBookPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, ComicBookConvert.INSTANCE::convertToDTO, ComicBookConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看漫画书籍详情")
    public CommonResult<ComicBookViewResp> view(String id) {
        ComicBookViewResp resp = super.doView(id, ComicBookConvert.INSTANCE::convertToViewResp);
        List<ComicAuthorDTO> comicAuthorDTOList = comicAuthorService.listByBookId(id);
        resp.setAuthorList(comicAuthorDTOList.stream().map(ComicBookConvert.INSTANCE::convertToViewResp).collect(Collectors.toList()));
        return CommonResult.success(resp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增漫画书籍")
    public CommonResult<ComicBookCreateResp> create(@RequestBody ComicBookCreateReq req) {
        return super.create(req, ComicBookConvert.INSTANCE::convertToDTO, ComicBookConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑漫画书籍")
    public CommonResult<Boolean> update(@RequestBody ComicBookUpdateReq req) {
        return super.update(req, ComicBookConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除漫画书籍")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

    @GetMapping("listPage")
    @ApiOperation(value = "查看漫画书籍所有页面")
    public CommonResult<List<ComicBookListPageResp>> listPage(String id) {
        List<Page> pageList = komgaApiService.listPageByBook(id);
        List<ComicBookListPageResp> result = pageList.stream().map(ComicBookConvert.INSTANCE::convertToListPageResp).collect(Collectors.toList());
        return CommonResult.success(result);
    }

    @PostMapping("uploadCover")
    @ApiOperation(value = "上传封面")
    public CommonResult<Boolean> uploadCover(@RequestBody ComicBookUploadCoverReq req) throws IOException {
        ComicBookDTO comicBookDTO = comicBookService.findById(req.getId());
        Path path = Paths.get(KaleidoUtils.getComicFolder(comicBookDTO.getPath()));
        String fileName = FilenameUtils.getBaseName(path.getFileName().toString());
        byte[] data = Base64.decode(RegExUtils.removeFirst(req.getData(), "data:image/.+;base64,"));
        if (comicBookDTO.getBookNumber() == null || comicBookDTO.getBookNumber() == 1) {
            Files.write(path.resolveSibling("cover.jpg"), data);
        }
        Files.write(path.getParent().resolve(fileName + ".jpg"), data);
        return CommonResult.success(true);
    }
}