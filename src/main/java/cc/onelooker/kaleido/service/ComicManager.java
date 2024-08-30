package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.*;
import cc.onelooker.kaleido.enums.AttributeType;
import cc.onelooker.kaleido.enums.AuthorRole;
import cc.onelooker.kaleido.enums.SubjectType;
import cc.onelooker.kaleido.enums.TaskType;
import cc.onelooker.kaleido.nfo.ComicInfoNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.third.komga.Author;
import cc.onelooker.kaleido.third.komga.Book;
import cc.onelooker.kaleido.third.komga.Link;
import cc.onelooker.kaleido.third.komga.Series;
import cc.onelooker.kaleido.third.tmm.Comic;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.third.tmm.TmmUtil;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.compress.CompressUtil;
import cn.hutool.extra.compress.extractor.Extractor;
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
    private ComicBookService comicBookService;

    @Autowired
    private ComicSeriesService comicSeriesService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AlternateTitleService alternateTitleService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private PathInfoService pathInfoService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TmmApiService tmmApiService;

    private Pattern pattern = Pattern.compile("[V|v]ol[.|_](\\d+)");

    @Transactional
    public void saveBook(ComicBookDTO comicBookDTO) {
        ComicBookDTO existComicBookDTO = comicBookService.findById(comicBookDTO.getId());
        if (existComicBookDTO == null) {
            comicBookService.insert(comicBookDTO);
        } else {
            comicBookService.update(comicBookDTO);
        }
        ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(comicBookDTO.getSeriesId());
        taskService.newTask(comicBookDTO.getId(), SubjectType.ComicBook, comicSeriesDTO.getTitle() + "【" + comicBookDTO.getTitle() + "】", TaskType.writeComicInfo);
    }

    @Transactional
    public void saveSeries(ComicSeriesDTO comicSeriesDTO) {
        try {
            attributeService.updateAttributes(comicSeriesDTO.getTagList(), comicSeriesDTO.getId(), AttributeType.Tag);
            alternateTitleService.updateTitles(comicSeriesDTO.getAlternateTitleList(), comicSeriesDTO.getId(), SubjectType.ComicSeries);
            authorService.updateAuthors(comicSeriesDTO.getWriterList(), comicSeriesDTO.getId(), AuthorRole.Writer);
            authorService.updateAuthors(comicSeriesDTO.getPencillerList(), comicSeriesDTO.getId(), AuthorRole.Penciller);
            String oldPath = comicSeriesDTO.getPath();
            renameDirIfChanged(comicSeriesDTO);
            ComicSeriesDTO existComicSeriesDTO = comicSeriesService.findById(comicSeriesDTO.getId());
            if (existComicSeriesDTO == null) {
                comicSeriesService.insert(comicSeriesDTO);
            } else {
                comicSeriesService.update(comicSeriesDTO);
            }
            List<ComicBookDTO> comicBookDTOList = comicBookService.listBySeriesId(comicSeriesDTO.getId());
            comicBookDTOList.forEach(s -> {
                if (!StringUtils.equals(oldPath, comicSeriesDTO.getPath())) {
                    //如果路径变化
                    s.setPath(StringUtils.replaceOnce(s.getPath(), oldPath, comicSeriesDTO.getPath()));
                    comicBookService.update(s);
                }
                //生成重写ComicInfo任务
                taskService.newTask(s.getId(), SubjectType.ComicBook, comicSeriesDTO.getTitle() + "【" + s.getTitle() + "】", TaskType.writeComicInfo);
            });
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Transactional
    public void syncSeries(Series series) {
        ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(series.getId());
        if (comicSeriesDTO == null) {
            comicSeriesDTO = new ComicSeriesDTO();
        }
        Series.Metadata metadata = series.getMetadata();
        Series.BooksMetadata booksMetadata = series.getBooksMetadata();
        comicSeriesDTO.setId(series.getId());
        comicSeriesDTO.setTitle(metadata.getTitle());
        comicSeriesDTO.setSummary(booksMetadata.getSummary());
        comicSeriesDTO.setBookCount(metadata.getTotalBookCount());
        comicSeriesDTO.setBgmId(getBgmId(metadata.getLinks()));
        comicSeriesDTO.setPublisher(metadata.getPublisher());
        Path path = KaleidoUtils.getComicBasicPath(series.getUrl());
        comicSeriesDTO.setPath(path.toString());
        comicSeriesDTO.setAddedAt(series.getAddedAt());
        comicSeriesDTO.setUpdatedAt(series.getUpdatedAt());
        comicSeriesDTO.setWriterList(transformAuthor(booksMetadata.getAuthors(), AuthorRole.Writer));
        comicSeriesDTO.setPencillerList(transformAuthor(booksMetadata.getAuthors(), AuthorRole.Penciller));
        comicSeriesDTO.setTagList(booksMetadata.getTags());
        //读取ComicInfo文件
        readComicInfo(comicSeriesDTO);
        saveSeries(comicSeriesDTO);
    }

    @Transactional
    public void syncBook(Book book) {
        ComicBookDTO comicBookDTO = comicBookService.findById(book.getId());
        if (comicBookDTO == null) {
            comicBookDTO = new ComicBookDTO();
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
        saveBook(comicBookDTO);
    }

    @Transactional
    public void matchPath(Path path, String bgmId) {
        Path importPath = KaleidoUtils.getComicImportPath();
        try {
            if (!Files.isDirectory(path)) {
                String baseName = FilenameUtils.getBaseName(path.getFileName().toString());
                path = importPath.resolve(baseName);
                if (Files.notExists(path)) {
                    Files.createDirectories(path);
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
            ExceptionUtil.wrapAndThrow(e);
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
                if (FileUtils.isEmptyDirectory(path.toFile())) {
                    NioFileUtils.deleteIfExists(path);
                    log.info("== 删除源文件夹:{}", path.getFileName());
                }
            } else {
                log.info("== 未找到匹配信息，直接移动文件");
                Path targetPath = KaleidoUtils.getComicPath(path.getFileName().toString());
                NioFileUtils.moveDir(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
            pathInfoService.deleteByPath(path.toString());
            log.info("== 清除文件夹信息记录:{}", path);
        } catch (Exception e) {
            log.error("更新源发生错误:{}", ExceptionUtil.getMessage(e));
        } finally {
            log.info("=========== 完成更新源 ==========");
        }
    }

    public ComicSeriesDTO findSeriesById(String seriesId) {
        ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(seriesId);
        List<AttributeDTO> attributeDTOList = attributeService.listBySubjectId(seriesId);
        List<AlternateTitleDTO> alternateTitleDTOList = alternateTitleService.listBySubjectId(seriesId);
        List<AuthorDTO> authorDTOList = authorService.listBySeriesId(seriesId);
        comicSeriesDTO.setAlternateTitleList(alternateTitleDTOList.stream().map(AlternateTitleDTO::getTitle).collect(Collectors.toList()));
        comicSeriesDTO.setTagList(attributeDTOList.stream().filter(s -> StringUtils.equals(s.getType(), AttributeType.Tag.name())).map(AttributeDTO::getValue).collect(Collectors.toList()));
        comicSeriesDTO.setWriterList(authorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), AuthorRole.Writer.name())).collect(Collectors.toList()));
        comicSeriesDTO.setPencillerList(authorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), AuthorRole.Penciller.name())).collect(Collectors.toList()));
        return comicSeriesDTO;
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

    private void renameDirIfChanged(ComicSeriesDTO comicSeriesDTO) throws IOException {
        String newPath = KaleidoUtils.genComicFolder(comicSeriesDTO);
        if (!StringUtils.equals(newPath, comicSeriesDTO.getPath())) {
            Path path = KaleidoUtils.getComicPath(newPath);
            if (Files.notExists(path)) {
                Files.createDirectories(path);
            }
            NioFileUtils.renameDir(KaleidoUtils.getComicPath(comicSeriesDTO.getPath()), path);
            comicSeriesDTO.setPath(newPath);
        }
    }

    private List<AuthorDTO> transformAuthor(List<Author> authorList, AuthorRole authorRole) {
        if (authorList == null) {
            return null;
        }
        return authorList.stream().filter(s -> s.getRole().equals(authorRole.name().toLowerCase())).map(s -> {
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setName(s.getName());
            return authorDTO;
        }).collect(Collectors.toList());
    }

    private void readComicInfo(ComicSeriesDTO comicSeriesDTO) {
        List<ComicBookDTO> comicBookDTOList = comicBookService.listBySeriesId(comicSeriesDTO.getId());
        if (CollectionUtils.isEmpty(comicBookDTOList)) {
            return;
        }
        ComicBookDTO comicBookDTO = comicBookDTOList.get(0);
        Path path = KaleidoUtils.getComicPath(comicBookDTO.getPath());
        Extractor extractor = CompressUtil.createExtractor(CharsetUtil.defaultCharset(), path.toFile());
        Path tempPath = Paths.get(System.getProperty("java.io.tmpdir"), "kaleido");
        extractor.extract(tempPath.toFile(), f -> StringUtils.equals(f.getName(), KaleidoConstants.COMIC_INFO));
        extractor.close();
        if (Files.notExists(tempPath.resolve(KaleidoConstants.COMIC_INFO))) {
            return;
        }
        ComicInfoNFO comicInfoNFO = NFOUtil.read(ComicInfoNFO.class, tempPath, KaleidoConstants.COMIC_INFO);
        if (comicInfoNFO == null) {
            return;
        }
        comicSeriesDTO.setYear(comicInfoNFO.getYear());
        comicSeriesDTO.setStatus(comicInfoNFO.getSeriesStatus());
        comicSeriesDTO.setBgmId(StringUtils.defaultString(comicSeriesDTO.getBgmId(), comicInfoNFO.getSeriesBgmId()));
        if (comicInfoNFO.getCommunityRating() != null) {
            comicSeriesDTO.setRating(Float.parseFloat(comicInfoNFO.getCommunityRating()));
        }
        comicSeriesDTO.setAlternateTitleList(comicInfoNFO.getAkas());
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

    private void unzipBook(Path path, Path folderPath) {
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
                        String fileName = s.getFileName().toString();
                        if (Files.isDirectory(s)) {
                            NioFileUtils.renameDir(s, tagetPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                        } else if (StringUtils.equalsAnyIgnoreCase(fileName, " thumbs.db", ".DS_Store")) {
                            Files.delete(s);
                        } else {
                            Files.move(s, tagetPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
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
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private void convertBook(Path path, Comic comic) {
        try {
            ComicSeriesDTO comicSeriesDTO = TmmUtil.toComicSeriesDTO(comic);
            String folderName = KaleidoUtils.genComicFolder(comicSeriesDTO);
            Path targetFolder = KaleidoUtils.getComicPath(folderName);
            String fileName = path.getFileName().toString();
            String baseName = FilenameUtils.getBaseName(fileName);
            String extension = FilenameUtils.getExtension(fileName);
            if (StringUtils.equalsAnyIgnoreCase(extension, "jpg", "jpeg", "png")) {
                log.info("== 移动图片文件:{}", fileName);
                Files.move(path, targetFolder.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                return;
            } else if (!StringUtils.equalsAnyIgnoreCase(extension, "zip", "cbz")) {
                log.info("== 忽略非压缩:{}", fileName);
                return;
            }

            //分析文件名，获取卷号
            Integer number = getVolumeNumber(fileName);
            Comic.Volume volume = getVolume(comic.getVolumes(), number);
            ComicBookDTO comicBookDTO = TmmUtil.toComicBookDTO(volume);
            if (comicBookDTO == null) {
                comicBookDTO = new ComicBookDTO();
            }
            if (StringUtils.isBlank(comicBookDTO.getTitle())) {
                comicBookDTO.setTitle(number != null ? "卷" + number : "全一卷");
            }
            comicBookDTO.setBookNumber(number);

            Path folderPath = path.resolveSibling(baseName);
            unzipBook(path, folderPath);
            ComicInfoNFO comicInfoNFO = NFOUtil.toComicInfoNFO(comicSeriesDTO, comicBookDTO);
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

    private Comic.Volume getVolume(List<Comic.Volume> volumes, Integer number) {
        if (CollectionUtils.isEmpty(volumes)) {
            return null;
        }
        if (volumes.size() < number) {
            return null;
        }
        return CollectionUtils.get(volumes, number);
    }

    private Integer getVolumeNumber(String fileName) {
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group(1));
        }
        return null;
    }

    private String getBgmId(List<Link> links) {
        if (links == null) {
            return null;
        }
        return links.stream().filter(link -> StringUtils.equalsAnyIgnoreCase(link.getLabel(), "Btv", "bgm.tv")).map(s -> StringUtils.substringAfterLast(s.getUrl(), "/")).findFirst().orElse(null);
    }

}
