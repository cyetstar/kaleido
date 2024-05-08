package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.comic.ComicBookDTO;
import cc.onelooker.kaleido.service.ComicManager;
import cc.onelooker.kaleido.service.comic.ComicBookService;
import cc.onelooker.kaleido.third.komga.Book;
import cc.onelooker.kaleido.third.komga.KomgaApiService;
import com.zjjcnt.common.core.domain.PageResult;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class ComicSyncRunnable extends AbstractEntityActionRunnable<Book> {

    private final KomgaApiService komgaApiService;

    private final ComicManager comicManager;

    private final ComicBookService comicBookService;

    private List<Book> bookList;

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
        PageResult<Book> bookPageResult = komgaApiService.pageBook(pageNumber - 1, pageSize);
        bookList = bookPageResult.getRecords();
        return bookPageResult;
    }

    @Override
    protected void processEntity(Book book) throws Exception {
        ComicBookDTO comicBookDTO = comicBookService.findById(book.getId());
        if (comicBookDTO == null || book.getUpdatedAt().compareTo(comicBookDTO.getUpdatedAt()) > 0) {
            comicManager.sync(book);
        }
    }

    @Override
    protected void afterRun(Map<String, String> params) {
        List<ComicBookDTO> comicBookDTOList = comicBookService.list(null);
        List<String> idList = comicBookDTOList.stream().map(ComicBookDTO::getId).collect(Collectors.toList());
        List<String> bookIdList = bookList.stream().map(Book::getId).collect(Collectors.toList());
        Collection<String> deleteIdList = CollectionUtils.subtract(idList, bookIdList);
        if (CollectionUtils.isNotEmpty(deleteIdList)) {
            for (String deleteId : deleteIdList) {
                comicBookService.deleteById(deleteId);
            }
        }
    }

    @Override
    protected String getMessage(Book book) {
        return book.getSeriesTitle() + "(" + book.getMetadata().getTitle() + ")";
    }
}
