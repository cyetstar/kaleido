package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.*;
import cc.onelooker.kaleido.enums.AttributeType;
import cc.onelooker.kaleido.enums.AuthorRole;
import cc.onelooker.kaleido.enums.SubjectType;
import cc.onelooker.kaleido.enums.TaskType;
import cc.onelooker.kaleido.nfo.ComicInfoNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.third.komga.Book;
import cc.onelooker.kaleido.third.komga.KomgaUtil;
import cc.onelooker.kaleido.third.komga.Series;
import cc.onelooker.kaleido.third.tmm.Comic;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.third.tmm.TmmUtil;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.compress.CompressUtil;
import cn.hutool.extra.compress.extractor.Extractor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Objects;
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

    @Transactional
    public void saveBook(ComicBookDTO comicBookDTO) {
        ComicBookDTO existComicBookDTO = comicBookService.findById(comicBookDTO.getId());
        if (existComicBookDTO == null) {
            comicBookService.insert(comicBookDTO);
        } else {
            comicBookService.update(comicBookDTO);
        }
        taskService.newTask(comicBookDTO.getId(), SubjectType.ComicBook, TaskType.writeComicInfo);
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
                taskService.newTask(s.getId(), SubjectType.ComicBook, TaskType.writeComicInfo);
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
        KomgaUtil.toComicSeriesDTO(comicSeriesDTO, series);
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
        KomgaUtil.toComicBookDTO(comicBookDTO, book);
        saveBook(comicBookDTO);
    }

    @Transactional
    public void matchPath(Path path, String bgmId) {
        try {
            ComicInfoNFO comicInfoNFO = new ComicInfoNFO();
            comicInfoNFO.setSeriesBgmId(bgmId);
            Path importPath = KaleidoUtils.getComicImportPath();
            String filename = FilenameUtils.getBaseName(path.getFileName().toString());
            Path newPath = importPath.resolve(StringUtils.defaultIfEmpty(bgmId, filename));
            if (Files.isDirectory(path)) {
                if (!StringUtils.equals(newPath.toString(), path.toString())) {
                    NioFileUtils.renameDir(path, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
                NFOUtil.write(comicInfoNFO, ComicInfoNFO.class, newPath, KaleidoConstants.COMIC_INFO);
            } else {
                if (Files.notExists(newPath)) {
                    Files.createDirectories(newPath);
                }
                NFOUtil.write(comicInfoNFO, ComicInfoNFO.class, newPath, KaleidoConstants.COMIC_INFO);
                Files.move(path, newPath.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    @Transactional
    public void updateSource(Path path) {
        if (!Files.isDirectory(path)) {
            return;
        }
        if (Files.exists(path.resolve(KaleidoConstants.COMIC_INFO))) {
            try {
                log.info("== 开始更新数据文件: {}", path);
                Comic comic = findTmmComicByNFO(path);
                if (comic != null) {
                    ComicSeriesDTO comicSeriesDTO = TmmUtil.toComicSeriesDTO(comic);
                    log.info("== 找到匹配信息: ({}) {}", comicSeriesDTO.getBgmId(), comicSeriesDTO.getTitle());
                    operationPath(comicSeriesDTO, path);
                } else {
                    log.info("== 未找到匹配信息，直接移动文件");
                    Path targetPath = KaleidoUtils.getComicPath(path.getFileName().toString());
                    NioFileUtils.moveDir(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (Exception e) {
                log.error("== 更新源发生错误: {}", path, e);
            } finally {
                log.info("== 完成更新源: {}", path);
            }
        }
    }

    @Transactional
    public void matchInfo(String seriesId, Comic comic) {
        if (comic == null) {
            return;
        }
        ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(seriesId);
        List<ComicBookDTO> comicBookDTOList = comicBookService.listBySeriesId(seriesId);
        TmmUtil.toComicSeriesDTO(comicSeriesDTO, comic);
        saveSeries(comicSeriesDTO);
        for (ComicBookDTO comicBookDTO : comicBookDTOList) {
            Comic.Volume volume = null;
            if (comic.getVolumes() != null) {
                volume = comic.getVolumes().stream().filter(s -> Objects.equals(s.getVolumeNumber(), comicBookDTO.getBookNumber())).findFirst().orElse(null);
            }
            TmmUtil.toComicBookDTO(comicBookDTO, volume);
            saveBook(comicBookDTO);
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
    public void writeComicInfo(ComicBookDTO comicBookDTO, ComicInfoNFO comicInfoNFO) {
        String baseName = FilenameUtils.getBaseName(comicBookDTO.getPath());
        Path zipPath = KaleidoUtils.getComicPath(comicBookDTO.getPath());
        Path folderPath = zipPath.resolveSibling(baseName);
        unzip(zipPath, folderPath);
        NFOUtil.write(comicInfoNFO, ComicInfoNFO.class, folderPath, KaleidoConstants.COMIC_INFO);
        zip(folderPath, zipPath);
    }

    private Comic findTmmComicByNFO(Path path) {
        try {
            ComicInfoNFO comicInfoNFO = NFOUtil.read(ComicInfoNFO.class, path, KaleidoConstants.COMIC_INFO);
            return tmmApiService.findComic(comicInfoNFO.getSeriesBgmId());
        } catch (Exception e) {
            log.info("== NFO文件无法读取: {}", path.resolve(KaleidoConstants.COMIC_INFO));
        }
        return null;
    }

    private void operationPath(ComicSeriesDTO comicSeriesDTO, Path path) {
        Path targetPath = createFolderPath(comicSeriesDTO);
        try {
            Files.list(path).forEach(s -> {
                try {
                    String fileName = s.getFileName().toString();
                    if (!KaleidoUtils.isComicZipFile(fileName)) {
                        return;
                    }
                    Integer volumeNumber = KaleidoUtils.parseVolumeNumber(fileName, 0);
                    moveExistingFilesToRecycleBin(targetPath, volumeNumber);

                    String baseName = FilenameUtils.getBaseName(fileName);
                    Path unzipBookPath = s.resolveSibling(baseName);
                    unzipBook(s, unzipBookPath);
                    ComicBookDTO comicBookDTO = comicSeriesDTO.getBook(volumeNumber);
                    if (comicBookDTO == null) {
                        comicBookDTO = new ComicBookDTO();
                        comicBookDTO.setTitle(volumeNumber == 0 ? "全一卷" : "卷 " + volumeNumber);
                        comicBookDTO.setBookNumber(volumeNumber);
                    }
                    ComicInfoNFO comicInfoNFO = NFOUtil.toComicInfoNFO(comicSeriesDTO, comicBookDTO);
                    NFOUtil.write(comicInfoNFO, ComicInfoNFO.class, unzipBookPath, KaleidoConstants.COMIC_INFO);
                    log.info("== 输出ComicInfo.xml: {}", unzipBookPath.resolve(KaleidoConstants.COMIC_INFO));
                    Path bookPath = targetPath.resolve(baseName + ".zip");
                    ZipUtil.zip(unzipBookPath.toString(), bookPath.toString());
                    log.info("== 输出目标文件: {}", bookPath);
                    NioFileUtils.deleteIfExists(unzipBookPath);
                    log.info("== 删除解压文件夹: {}", unzipBookPath);
                    Files.delete(s);
                    log.info("== 删除原文件: {}", s);
                } catch (Exception e) {
                    ExceptionUtil.wrapAndThrow(e);
                }
            });
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
        //删除空文件夹
        deletePathIfEmpty(path);
    }

    private void moveExistingFilesToRecycleBin(Path targetPath, Integer volumeNumber) {
        try {
            Path recyclePath = KaleidoUtils.getComicRecyclePath();
            Files.list(targetPath.resolveSibling(targetPath)).forEach(s -> {
                try {
                    String fileName = s.getFileName().toString();
                    Integer number = KaleidoUtils.parseVolumeNumber(fileName, 0);
                    if (KaleidoUtils.isComicZipFile(fileName) && Objects.equals(number, volumeNumber)) {
                        Files.move(s, recyclePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                        log.info("== 移除待替换漫画文件: {}", s.getFileName());
                    }
                } catch (IOException e) {
                    ExceptionUtil.wrapAndThrow(e);
                }
            });
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private Path createFolderPath(ComicSeriesDTO comicSeriesDTO) {
        try {
            String folderName = KaleidoUtils.genComicFolder(comicSeriesDTO);
            Path folderPath = KaleidoUtils.getComicPath(folderName);
            if (Files.notExists(folderPath)) {
                Files.createDirectories(folderPath);
            }
            return folderPath;
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
            return null;
        }
    }

    private void deletePathIfEmpty(Path path) {
        try {
            if (Files.list(path).noneMatch(s -> KaleidoUtils.isComicZipFile(s.getFileName().toString()))) {
                NioFileUtils.deleteIfExists(path);
                log.info("== 删除源文件夹: {}", path.getFileName());
            }
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
    }

    private void renameDirIfChanged(ComicSeriesDTO comicSeriesDTO) throws IOException {
        String comicFolder = KaleidoUtils.genComicFolder(comicSeriesDTO);
        if (!StringUtils.equals(comicFolder, comicSeriesDTO.getPath())) {
            Path newPath = KaleidoUtils.getComicPath(comicFolder);
            Path oldPath = KaleidoUtils.getComicPath(comicSeriesDTO.getPath());
            if (Files.notExists(newPath)) {
                Files.createDirectories(newPath);
            }
            if (Files.exists(oldPath)) {
                NioFileUtils.renameDir(oldPath, newPath);
            } else {
                log.info("原目录不存在，源数据可能存在目录不一致情况。{}", oldPath);
            }
        }
        comicSeriesDTO.setPath(comicFolder);
    }

    private void readComicInfo(ComicSeriesDTO comicSeriesDTO) {
        try {
            Path path = KaleidoUtils.getComicPath(comicSeriesDTO.getPath());
            Path zipPath = Files.list(path).filter(s -> StringUtils.equalsAny(FilenameUtils.getExtension(s.getFileName().toString()), "zip", "cbz")).findFirst().orElse(null);
            if (zipPath == null) {
                return;
            }
            Extractor extractor = CompressUtil.createExtractor(CharsetUtil.defaultCharset(), zipPath.toFile());
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
            comicSeriesDTO.setBgmId(StringUtils.defaultIfEmpty(comicSeriesDTO.getBgmId(), comicInfoNFO.getSeriesBgmId()));
            comicSeriesDTO.setRating(NumberUtil.parseFloat(comicInfoNFO.getCommunityRating(), null));
            comicSeriesDTO.setAlternateTitleList(comicInfoNFO.getAkas());
        } catch (IOException e) {
            log.error("== 读取漫画信息异常: {}", ExceptionUtil.getMessage(e));
            ExceptionUtil.wrapAndThrow(e);
        }
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
        log.info("== 解压文件: {}", folderPath);
        moveBookImage(folderPath, folderPath);
        log.info("== 移动图片文件: {}", folderPath);
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
                        log.info("移动图片文件发生错误: {}", ExceptionUtil.getMessage(e));
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

}
