package cc.onelooker.kaleido.third.komga;

import cc.onelooker.kaleido.dto.AuthorDTO;
import cc.onelooker.kaleido.dto.ComicBookDTO;
import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import cc.onelooker.kaleido.enums.AuthorRole;
import cc.onelooker.kaleido.utils.KaleidoUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xiadawei
 * @Date 2024-09-02 20:50:00
 * @Description TODO
 */
public class KomgaUtil {
    public static void toComicSeriesDTO(ComicSeriesDTO comicSeriesDTO, Series series) {
        Series.Metadata metadata = series.getMetadata();
        Series.BooksMetadata booksMetadata = series.getBooksMetadata();
        comicSeriesDTO.setId(series.getId());
        comicSeriesDTO.setTitle(metadata.getTitle());
        comicSeriesDTO.setSummary(booksMetadata.getSummary());
        comicSeriesDTO.setBgmId(getBgmId(metadata.getLinks()));
        comicSeriesDTO.setPublisher(metadata.getPublisher());
        Path path = KaleidoUtil.getComicBasicPath(series.getUrl());
        comicSeriesDTO.setPath(path.toString());
        comicSeriesDTO.setAddedAt(series.getAddedAt());
        comicSeriesDTO.setUpdatedAt(series.getUpdatedAt());
        if (booksMetadata.getAuthors() != null) {
            comicSeriesDTO.setWriterList(booksMetadata.getAuthors().stream().filter(s -> StringUtils.equalsIgnoreCase(s.getRole(), AuthorRole.Writer.name())).map(KomgaUtil::toAuthorDTO).collect(Collectors.toList()));
            comicSeriesDTO.setPencillerList(booksMetadata.getAuthors().stream().filter(s -> StringUtils.equalsIgnoreCase(s.getRole(), AuthorRole.Penciller.name())).map(KomgaUtil::toAuthorDTO).collect(Collectors.toList()));
        }
        comicSeriesDTO.setTagList(booksMetadata.getTags());
    }

    public static void toComicBookDTO(ComicBookDTO comicBookDTO, Book book) {
        Book.Media media = book.getMedia();
        Book.Metadata metadata = book.getMetadata();
        comicBookDTO.setId(book.getId());
        comicBookDTO.setSeriesId(book.getSeriesId());
        comicBookDTO.setTitle(metadata.getTitle());
        comicBookDTO.setBookNumber(metadata.getNumber());
        comicBookDTO.setSortNumber(metadata.getNumberSort());
        comicBookDTO.setPageCount(media.getPagesCount());
        String filename = FilenameUtils.getName(book.getUrl());
        comicBookDTO.setFilename(filename);
        comicBookDTO.setBgmId(getBgmId(metadata.getLinks()));
        comicBookDTO.setFileSize(book.getSizeBytes());
        comicBookDTO.setAddedAt(book.getAddedAt());
        comicBookDTO.setUpdatedAt(book.getUpdatedAt());
    }

    private static AuthorDTO toAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(author.getName());
        return authorDTO;
    }

    private static String getBgmId(List<Link> links) {
        if (links == null) {
            return null;
        }
        return links.stream().filter(link -> StringUtils.equalsAnyIgnoreCase(link.getLabel(), "Btv", "bgm.tv")).map(s -> StringUtils.substringAfterLast(s.getUrl(), "/")).findFirst().orElse(null);
    }

}
