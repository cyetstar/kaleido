package cc.onelooker.kaleido.thread;

import cc.onelooker.kaleido.dto.comic.ComicBookDTO;
import cc.onelooker.kaleido.service.ComicManager;
import cc.onelooker.kaleido.service.comic.ComicBookService;
import cc.onelooker.kaleido.third.komga.Book;
import cc.onelooker.kaleido.third.komga.KomgaApiService;
import com.zjjcnt.common.core.domain.PageResult;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by cyetstar on 2021/1/7.
 */
@Component
public class ComicSyncRunnable extends AbstractEntityActionRunnable<Book> {

    private final KomgaApiService komgaApiService;

    private final ComicManager comicManager;

    private final ComicBookService comicBookService;

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
        return komgaApiService.pageBook(pageNumber - 1, pageSize);
    }

    @Override
    protected void processEntity(Book book) throws Exception {
        ComicBookDTO comicBookDTO = comicBookService.findById(book.getId());
        if (comicBookDTO == null || book.getUpdatedAt().compareTo(comicBookDTO.getUpdatedAt()) > 0) {
            comicManager.syncBook(book);
        }
    }

    @Override
    protected String getMessage(Book book) {
        return book.getSeriesTitle() + "(" + book.getMetadata().getTitle() + ")";
    }
}
