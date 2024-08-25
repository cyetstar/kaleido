package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.AlternateTitleDTO;
import cc.onelooker.kaleido.dto.AttributeDTO;
import cc.onelooker.kaleido.dto.PathInfoDTO;
import cc.onelooker.kaleido.dto.SubjectAttributeDTO;
import cc.onelooker.kaleido.dto.ComicAuthorDTO;
import cc.onelooker.kaleido.dto.ComicBookDTO;
import cc.onelooker.kaleido.dto.ComicSeriesAuthorDTO;
import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import cc.onelooker.kaleido.enums.AttributeType;
import cc.onelooker.kaleido.enums.AuthorRole;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.nfo.ComicInfoNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.third.komga.*;
import cc.onelooker.kaleido.third.tmm.Comic;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.compress.CompressUtil;
import cn.hutool.extra.compress.extractor.Extractor;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author xiadawei
 * @Date 2024-03-12 20:15:00
 * @Description TODO
 */
@Slf4j
@Component
public class ComicManager {

    @Autowired
    private KomgaApiService komgaApiService;

    @Autowired
    private ComicBookService comicBookService;

    @Autowired
    private ComicSeriesService comicSeriesService;

    @Autowired
    private ComicAuthorService comicAuthorService;

    @Autowired
    private ComicSeriesAuthorService comicSeriesAuthorService;

    @Autowired
    private AlternateTitleService alternateTitleService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private SubjectAttributeService subjectAttributeService;

    @Autowired
    private PathInfoService pathInfoService;

    @Autowired
    private TmmApiService tmmApiService;

    private Pattern pattern = Pattern.compile("[V|v]ol[.|_](\\d+)");

    @Transactional
    public void syncBook(Book book) {
        ComicBookDTO comicBookDTO = comicBookService.findById(book.getId());
        boolean isNew = false;
        if (comicBookDTO == null) {
            comicBookDTO = new ComicBookDTO();
            isNew = true;
        }
        Book.Media media = book.getMedia();
        Book.Metadata metadata = book.getMetadata();
        comicBookDTO.setId(book.getId());
        comicBookDTO.setSeriesId(book.getSeriesId());
        comicBookDTO.setTitle(metadata.getTitle());
        comicBookDTO.setBookNumber(metadata.getNumber());
        comicBookDTO.setSortNumber(metadata.getNumberSort());
        comicBookDTO.setPageCount(media.getPagesCount());
        comicBookDTO.setPath(book.getUrl());
        comicBookDTO.setBgmId(getBgmId(metadata.getLinks()));
        comicBookDTO.setFileSize(book.getSizeBytes());
        comicBookDTO.setAddedAt(book.getAddedAt());
        comicBookDTO.setUpdatedAt(book.getUpdatedAt());
        if (isNew) {
            comicBookService.insert(comicBookDTO);
        } else {
            //如果路径变更，则更新路径
            String newPath = getNewPathIfChanged(book);
            comicBookDTO.setPath(newPath);
            comicBookService.update(comicBookDTO);
        }
    }

    private String getNewPathIfChanged(Book book) {
        List<Author> authors = book.getMetadata().getAuthors();
        String writerName = authors.stream().filter(s -> StringUtils.equals(s.getRole(), "writer")).findFirst().map(Author::getName).orElse(null);
        String pencillerName = authors.stream().filter(s -> StringUtils.equals(s.getRole(), "penciller")).findFirst().map(Author::getName).orElse(null);
        String comicFolder = KaleidoUtils.genComicFolder(book.getSeriesTitle(), writerName, pencillerName);
        String fullPath = FilenameUtils.getFullPath(book.getUrl());
        String name = FilenameUtils.getName(book.getUrl());
        if (StringUtils.endsWith(fullPath, comicFolder)) {
            return book.getUrl();
        }
        Path path = Paths.get(ConfigUtils.getSysConfig(ConfigKey.komgaComicLibraryPath), comicFolder, name);
        try {
            Path oldPath = KaleidoUtils.getComicPath(book.getUrl());
            Path newPath = KaleidoUtils.getComicPath(path.toString());
            Files.move(oldPath, newPath);
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
        return path.toString();
    }

    private String getNewPathIfChanged(Series series) {
        List<Author> authors = series.getBooksMetadata().getAuthors();
        String writerName = authors.stream().filter(s -> StringUtils.equals(s.getRole(), "writer")).findFirst().map(Author::getName).orElse(null);
        String pencillerName = authors.stream().filter(s -> StringUtils.equals(s.getRole(), "penciller")).findFirst().map(Author::getName).orElse(null);
        String comicFolder = KaleidoUtils.genComicFolder(series.getMetadata().getTitle(), writerName, pencillerName);
        if (StringUtils.endsWith(series.getUrl(), comicFolder)) {
            return series.getUrl();
        }
        Path path = Paths.get(ConfigUtils.getSysConfig(ConfigKey.komgaComicLibraryPath), comicFolder);
        try {
            Path oldPath = KaleidoUtils.getComicPath(series.getUrl());
            Path newPath = KaleidoUtils.getComicPath(path.toString());
            NioFileUtils.renameDir(oldPath, newPath);
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
        return path.toString();
    }

    public void syncSeries(Series series) {
        if (series == null) {
            return;
        }
        ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(series.getId());
        boolean isNew = false;
        if (comicSeriesDTO == null) {
            comicSeriesDTO = new ComicSeriesDTO();
            isNew = true;
        }
        Series.Metadata metadata = series.getMetadata();
        Series.BooksMetadata booksMetadata = series.getBooksMetadata();
        comicSeriesDTO.setId(series.getId());
        comicSeriesDTO.setTitle(StringUtils.defaultIfEmpty(comicSeriesDTO.getTitle(), metadata.getTitle()));
        comicSeriesDTO.setSummary(StringUtils.defaultIfEmpty(comicSeriesDTO.getSummary(), booksMetadata.getSummary()));
        comicSeriesDTO.setBookCount(metadata.getTotalBookCount());
        comicSeriesDTO.setBgmId(StringUtils.defaultIfEmpty(comicSeriesDTO.getBgmId(), getBgmId(metadata.getLinks())));
        comicSeriesDTO.setPublisher(StringUtils.defaultIfEmpty(comicSeriesDTO.getPublisher(), metadata.getPublisher()));
        comicSeriesDTO.setPath(StringUtils.defaultIfEmpty(comicSeriesDTO.getPath(), series.getUrl()));
        comicSeriesDTO.setStatus(StringUtils.defaultIfEmpty(comicSeriesDTO.getStatus(), metadata.getStatus()));
        comicSeriesDTO.setAddedAt(series.getAddedAt());
        comicSeriesDTO.setUpdatedAt(series.getUpdatedAt());
        if (isNew) {
            comicSeriesService.insert(comicSeriesDTO);
        } else {
            String newPath = getNewPathIfChanged(series);
            comicSeriesDTO.setPath(newPath);
            comicSeriesService.update(comicSeriesDTO);
        }
        syncAuthor(booksMetadata.getAuthors(), comicSeriesDTO);
        syncAttribute(booksMetadata.getTags(), comicSeriesDTO.getId(), AttributeType.ComicTag);

    }

    @Transactional
    public void syncSeries(String seriesId) {
        Series series = komgaApiService.findSeries(seriesId);
        syncSeries(series);
        List<Book> bookList = komgaApiService.listBookBySeries(seriesId);
        List<String> bookIdList = Lists.newArrayList();
        for (Book book : bookList) {
            syncBook(book);
            bookIdList.add(book.getId());
        }
        List<ComicBookDTO> comicBookDTOList = comicBookService.listBySeriesId(seriesId);
        List<String> idList = comicBookDTOList.stream().map(ComicBookDTO::getId).collect(Collectors.toList());
        CollectionUtils.subtract(idList, bookIdList).forEach(deleteId -> comicBookService.deleteById(deleteId));
    }

    @Transactional
    public void matchPath(Path path, String bgmId) {
        Path importPath = KaleidoUtils.getComicImportPath();
        try {
            if (!Files.isDirectory(path)) {
                String baseName = FilenameUtils.getBaseName(path.getFileName().toString());
                path = importPath.resolve(baseName);
                if (!Files.exists(path)) {
                    path.toFile().mkdir();
                }
            }
            PathInfoDTO pathInfoDTO = pathInfoService.findByPath(path.toString());
            if (pathInfoDTO == null) {
                pathInfoService.insert(path.toString(), bgmId);
            } else {
                pathInfoDTO.setBgmId(bgmId);
                pathInfoService.update(pathInfoDTO);
            }
        } catch (Exception e) {
            log.error("文件夹匹配信息发生错误", e);
        }
    }

    @Transactional
    public void updateSource(Path path) {
        try {
            log.info("=========== 开始更新源 ==========");
            log.info("== 源文件信息:{}", path);
            boolean isDirectory = Files.isDirectory(path);
            if (!isDirectory) {
                log.info("== 忽略不是文件夹的文件:{}", path);
                return;
            }
            PathInfoDTO pathInfoDTO = pathInfoService.findByPath(path.toString());
            if (pathInfoDTO == null || StringUtils.isEmpty(pathInfoDTO.getBgmId())) {
                log.info("== 忽略找不到匹配信息的文件:{}", path);
                return;
            }
            Comic comic = tmmApiService.findComic(pathInfoDTO.getBgmId());
            if (comic != null) {
                log.info("== 找到匹配信息:({}){}", comic.getBgmId(), comic.getSeries());
                Files.list(path).forEach(s -> convertBook(s, comic));
            } else {
                log.info("== 未找到匹配信息，直接移动文件");
                Path targetFolder = KaleidoUtils.getComicPath(path.getFileName().toString());
                Files.list(path).forEach(s -> {
                    try {
                        String fileName = s.getFileName().toString();
                        String extension = FilenameUtils.getExtension(fileName);
                        if (StringUtils.equalsAnyIgnoreCase(extension, "jpg", "jpeg", "png", "zip", "cbz")) {
                            Files.move(path, targetFolder.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (Exception e) {
                        log.error("更新源发生错误:{}", ExceptionUtil.getMessage(e));
                    }
                });

            }
            if (FileUtils.isEmptyDirectory(path.toFile())) {
                NioFileUtils.deleteIfExists(path);
                log.info("== 删除源文件夹:{}", path.getFileName());
            }
            pathInfoService.deleteByPath(path.toString());
            log.info("== 清除文件夹信息记录:{}", path);
        } catch (Exception e) {
            log.error("更新源发生错误:{}", ExceptionUtil.getMessage(e));
        } finally {
            log.info("=========== 完成更新源 ==========");
        }
    }

    @Transactional
    public void readComicInfo(String seriesId) {
        List<ComicBookDTO> comicBookDTOList = comicBookService.listBySeriesId(seriesId);
        if (CollectionUtils.isEmpty(comicBookDTOList)) {
            return;
        }
        ComicBookDTO comicBookDTO = comicBookDTOList.get(0);
        Path path = KaleidoUtils.getComicPath(comicBookDTO.getPath());
        Extractor extractor = CompressUtil.createExtractor(CharsetUtil.defaultCharset(), path.toFile());
        Path tempPath = Paths.get(System.getProperty("java.io.tmpdir"), "kaleido");
        extractor.extract(tempPath.toFile(), f -> StringUtils.equals(f.getName(), KaleidoConstants.COMIC_INFO));
        extractor.close();
        ComicInfoNFO comicInfoNFO = NFOUtil.read(ComicInfoNFO.class, tempPath, KaleidoConstants.COMIC_INFO);
        if (comicInfoNFO == null) {
            return;
        }
        ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(seriesId);
        comicSeriesDTO.setYear(StringUtils.defaultString(comicSeriesDTO.getYear(), comicInfoNFO.getYear()));
        comicSeriesDTO.setStatus(comicInfoNFO.getSeriesStatus());
        comicSeriesDTO.setBgmId(StringUtils.defaultString(comicSeriesDTO.getBgmId(), comicInfoNFO.getSeriesBgmId()));
        if (comicSeriesDTO.getRating() == null) {
            comicSeriesDTO.setRating(Float.parseFloat(comicInfoNFO.getCommunityRating()));
        }
        comicSeriesService.update(comicSeriesDTO);
        syncAlternateTitle(comicInfoNFO.getAkas(), seriesId);
    }

    @Transactional
    public void writeComicInfo(String seriesId) {
        ComicSeriesDTO comicSeriesDTO = findSeriesById(seriesId);
        List<ComicBookDTO> comicBookDTOList = comicBookService.listBySeriesId(seriesId);
        comicBookDTOList.forEach(s -> {
            String baseName = FilenameUtils.getBaseName(s.getPath());
            Path zipPath = KaleidoUtils.getComicPath(s.getPath());
            Path folderPath = zipPath.resolveSibling(baseName);
            unzip(zipPath, folderPath);
            ComicInfoNFO comicInfoNFO = NFOUtil.toComicInfoNFO(comicSeriesDTO, s);
            NFOUtil.write(comicInfoNFO, ComicInfoNFO.class, folderPath, KaleidoConstants.COMIC_INFO);
            zip(folderPath, zipPath);
        });
    }

    @Transactional
    public void writeComicInfo(ComicBookDTO comicBookDTO) {
        String baseName = FilenameUtils.getBaseName(comicBookDTO.getPath());
        Path zipPath = KaleidoUtils.getComicPath(comicBookDTO.getPath());
        Path folderPath = zipPath.resolveSibling(baseName);
        unzip(zipPath, folderPath);
        ComicSeriesDTO comicSeriesDTO = findSeriesById(comicBookDTO.getSeriesId());
        ComicInfoNFO comicInfoNFO = NFOUtil.toComicInfoNFO(comicSeriesDTO, comicBookDTO);
        NFOUtil.write(comicInfoNFO, ComicInfoNFO.class, folderPath, KaleidoConstants.COMIC_INFO);
        zip(folderPath, zipPath);
    }

    private ComicSeriesDTO findSeriesById(String seriesId) {
        ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(seriesId);
        comicSeriesAuthorService.listBySeriesId(seriesId).forEach(s -> {
            ComicAuthorDTO comicAuthorDTO = comicAuthorService.findById(s.getAuthorId());
            if (StringUtils.equals(s.getRole(), AuthorRole.Writer.name())) {
                comicSeriesDTO.setWriterName(comicAuthorDTO.getName());
            } else {
                comicSeriesDTO.setPencillerName(comicAuthorDTO.getName());
            }
        });
        List<AlternateTitleDTO> alternateTitleDTOList = alternateTitleService.listBySubjectId(seriesId);
        List<AttributeDTO> attributeDTOList = attributeService.listBySubjectId(seriesId);
        List<String> alternateTitleList = alternateTitleDTOList.stream().map(AlternateTitleDTO::getTitle).collect(Collectors.toList());
        comicSeriesDTO.setAlternateTitleList(alternateTitleList);
        Map<String, List<AttributeDTO>> attributeMap = attributeDTOList.stream().collect(Collectors.groupingBy(AttributeDTO::getType));
        comicSeriesDTO.setTagList(attributeMap.getOrDefault(AttributeType.ComicTag.name(), Lists.newArrayList()).stream().map(AttributeDTO::getValue).collect(Collectors.toList()));
        return comicSeriesDTO;
    }

    private void unzip(Path zipPath, Path folderPath) {
        Extractor extractor = CompressUtil.createExtractor(CharsetUtil.defaultCharset(), zipPath.toFile());
        extractor.extract(folderPath.toFile(), archiveEntry -> StringUtils.equalsAnyIgnoreCase(FilenameUtils.getExtension(archiveEntry.getName()), "jpg", "jpeg", "png", "xml"));
        extractor.close();
    }

    private void zip(Path folderPath, Path zipPath) {
        try {
            ZipUtil.zip(folderPath.toString(), zipPath.toString());
            NioFileUtils.deleteIfExists(folderPath);
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    public void unzipBook(Path path, Path folderPath) {
        Extractor extractor = CompressUtil.createExtractor(CharsetUtil.defaultCharset(), path.toFile());
        extractor.extract(folderPath.toFile(), archiveEntry -> StringUtils.equalsAnyIgnoreCase(FilenameUtils.getExtension(archiveEntry.getName()), "jpg", "jpeg", "png", "xml"));
        extractor.close();
        log.info("== 解压文件:{}", folderPath);
        moveBookImage(folderPath, folderPath);
        log.info("== 移动图片文件:{}", folderPath);
    }

    private void moveBookImage(Path folderPath, Path tagetPath) {
        try {
            if (Files.list(folderPath).filter(Files::isDirectory).count() == 1) {
                Files.list(folderPath).forEach(s -> moveBookImage(s, tagetPath));
            } else {
                Files.list(folderPath).forEach(s -> {
                    try {
                        if (Files.isDirectory(s)) {
                            NioFileUtils.renameDir(s, tagetPath.resolve(s.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                        } else if (StringUtils.equalsAnyIgnoreCase(s.getFileName().toString(), " thumbs.db", ".DS_Store")) {
                            Files.delete(s);
                        } else {
                            Files.move(s, tagetPath.resolve(s.getFileName().toString()), StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException e) {
                        log.info("移动图片文件发生错误:{}", ExceptionUtil.getMessage(e));
                    }
                });
            }
            if (FileUtils.isEmptyDirectory(folderPath.toFile())) {
                NioFileUtils.deleteIfExists(folderPath);
            }
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    private void convertBook(Path path, Comic comic) {
        try {
            ComicInfoNFO comicInfoNFO = NFOUtil.toComicInfoNFO(comic);
            String fileName = path.getFileName().toString();
            String extension = FilenameUtils.getExtension(fileName);
            Path targetFolder = KaleidoUtils.getComicPath(KaleidoUtils.genComicFolder(comicInfoNFO.getSeries(), comicInfoNFO.getWriter(), comicInfoNFO.getPenciller()));
            if (StringUtils.equalsAnyIgnoreCase(extension, "jpg", "jpeg", "png")) {
                log.info("== 移动图片文件:{}", fileName);
                Files.move(path, targetFolder.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                return;
            } else if (!StringUtils.equalsAnyIgnoreCase(extension, "zip", "cbz")) {
                log.info("== 忽略非压缩:{}", fileName);
                return;
            }
            String baseName = FilenameUtils.getBaseName(fileName);
            Path folderPath = path.getParent().resolve(baseName);
            unzipBook(path, folderPath);

            Integer number = getVolumeNumber(path.getFileName().toString());
            if (number != null) {
                comicInfoNFO.setTitle("卷" + number);
                comicInfoNFO.setNumber(String.valueOf(number));
            }
            List<Comic.Volume> volumes = comic.getVolumes();
            if (number != null && number <= volumes.size()) {
                Comic.Volume volume = volumes.get(number - 1);
                if (volume != null) {
                    comicInfoNFO.setWeb("https://bgm.tv/subject/" + volume.getBgmId());
                    comicInfoNFO.setTitle(volume.getTitle());
                    comicInfoNFO.setNumber(String.valueOf(number));
                }
            }
            if (StringUtils.isEmpty(comicInfoNFO.getTitle())) {
                comicInfoNFO.setTitle(comic.getSeries());
            }
            NFOUtil.write(comicInfoNFO, ComicInfoNFO.class, folderPath, KaleidoConstants.COMIC_INFO);
            log.info("== 输出ComicInfo.xml:{}", folderPath);
            Path targetPath = targetFolder.resolve(baseName + ".zip");
            ZipUtil.zip(folderPath.toString(), targetPath.toString());
            log.info("== 输出目标文件:{}", targetPath);
            NioFileUtils.deleteIfExists(folderPath);
            log.info("== 删除解压文件夹:{}", folderPath);
            Files.delete(path);
            log.info("== 删除原文件:{}", path);
        } catch (Exception e) {
            log.info("== 转换压缩文件失败:{}", ExceptionUtil.getMessage(e));
        }
    }

    private Integer getVolumeNumber(String fileName) {
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group(1));
        }
        return null;
    }

    private void syncAuthor(List<Author> authors, ComicSeriesDTO comicSeriesDTO) {
        List<ComicSeriesAuthorDTO> comicSeriesAuthorDTOList = comicSeriesAuthorService.listBySeriesId(comicSeriesDTO.getId());
        if (CollectionUtils.isNotEmpty(comicSeriesAuthorDTOList)) {
            return;
        }
        if (authors == null) {
            return;
        }
        for (Author author : authors) {
            ComicAuthorDTO comicAuthorDTO = comicAuthorService.findByName(author.getName());
            if (comicAuthorDTO == null) {
                comicAuthorDTO = comicAuthorService.insert(author.getName());
            }
            comicSeriesAuthorService.insert(comicSeriesDTO.getId(), comicAuthorDTO.getId(), AuthorRole.valueOf(author.getRole()));
        }
    }

    private void syncAlternateTitle(List<String> alternateTitles, String subjectId) {
        List<AlternateTitleDTO> alternateTitleDTOList = alternateTitleService.listBySubjectId(subjectId);
        if (CollectionUtils.isNotEmpty(alternateTitleDTOList)) {
            return;
        }
        if (alternateTitles == null) {
            return;
        }
        for (String alternateTitle : alternateTitles) {
            AlternateTitleDTO alternateTitleDTO = new AlternateTitleDTO();
            alternateTitleDTO.setTitle(alternateTitle);
            alternateTitleDTO.setSubjectId(subjectId);
            alternateTitleDTO.setSubjectType("comic_series");
            alternateTitleService.insert(alternateTitleDTO);
        }
    }

    private void syncAttribute(List<String> attributes, String subjectId, AttributeType type) {
        List<SubjectAttributeDTO> subjectAttributeDTOList = subjectAttributeService.listBySubjectIdAndAttributeType(subjectId, type);
        if (CollectionUtils.isNotEmpty(subjectAttributeDTOList)) {
            return;
        }
        if (attributes == null) {
            return;
        }
        for (String value : attributes) {
            AttributeDTO attributeDTO = attributeService.findByValueAndType(value, type);
            if (attributeDTO == null) {
                attributeDTO = attributeService.insert(value, type);
            }
            subjectAttributeService.insert(subjectId, attributeDTO.getId());
        }
    }

    private String getBgmId(List<Link> links) {
        if (links == null) {
            return null;
        }
        return links.stream().filter(link -> StringUtils.equalsAnyIgnoreCase(link.getLabel(), "Btv", "bgm.tv")).map(s -> StringUtils.substringAfterLast(s.getUrl(), "/")).findFirst().orElse(null);
    }

}
