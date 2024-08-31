package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.convert.ComicBookConvert;
import cc.onelooker.kaleido.dto.ComicBookDTO;
import cc.onelooker.kaleido.service.ComicBookService;
import cc.onelooker.kaleido.service.ComicManager;
import cc.onelooker.kaleido.third.komga.Book;
import cc.onelooker.kaleido.third.komga.KomgaApiService;
import cc.onelooker.kaleido.third.komga.Series;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class ComicSyncRunnable extends AbstractEntityActionRunnable<Book> {

    private final KomgaApiService komgaApiService;

    private final ComicManager comicManager;

    private final ComicBookService comicBookService;

    private final List<String> bookIdList = Lists.newArrayList();

    private Set<String> seriesIdCache = Sets.newHashSet();

    public ComicSyncRunnable(KomgaApiService komgaApiService, ComicManager comicManager, ComicBookService comicBookService) {
        this.komgaApiService = komgaApiService;
        this.comicManager = comicManager;
        this.comicBookService = comicBookService;
    }

    @Override
    public Action getAction() {
        return Action.comicSync;
    }

    @Override
    protected PageResult<Book> page(Map<String, String> params, int pageNumber, int pageSize) {
        String seriesId = MapUtils.getString(params, "seriesId");
        PageResult<Book> bookPageResult = null;
        if (StringUtils.isEmpty(seriesId)) {
            bookPageResult = komgaApiService.pageBook(pageNumber - 1, pageSize);
            bookIdList.addAll(bookPageResult.getRecords().stream().map(Book::getId).collect(Collectors.toList()));
        } else if (pageNumber == 1) {
            List<Book> records = komgaApiService.listBookBySeries(seriesId);
            bookPageResult = PageResult.convert(Page.of(pageNumber, pageSize, records.size()), records);
        }
        return bookPageResult;
    }

    @Override
    protected int processEntity(Map<String, String> params, Book book) throws Exception {
        ComicBookDTO comicBookDTO = comicBookService.findById(book.getId());
        if (comicBookDTO == null || book.getUpdatedAt().compareTo(comicBookDTO.getUpdatedAt()) > 0 || MapUtils.getBooleanValue(params, "force")) {
            //同步komga
            comicManager.syncBook(book);
            if (seriesIdCache.add(book.getSeriesId())) {
                Series series = komgaApiService.findSeries(book.getSeriesId());
                comicManager.syncSeries(series);
            }
            return SUCCESS;
        }
        return IGNORE;
    }

    @Override
    protected void afterRun(Map<String, String> params) {
        String seriesId = MapUtils.getString(params, "seriesId");
        if (StringUtils.isNotEmpty(seriesId)) {
            //单条记录同步，不做后续处理
            return;
        }
        ComicBookDTO param = ComicBookConvert.INSTANCE.convertToDTO(params);
        List<ComicBookDTO> comicBookDTOList = comicBookService.list(param);
        List<String> idList = comicBookDTOList.stream().map(ComicBookDTO::getId).collect(Collectors.toList());
        Collection<String> deleteIdList = CollectionUtils.subtract(idList, bookIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (String deleteId : deleteIdList) {
                comicBookService.deleteById(deleteId);
            }
        }
        seriesIdCache.clear();
        bookIdList.clear();
    }

}
