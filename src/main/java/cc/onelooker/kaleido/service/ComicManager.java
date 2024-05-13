package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.AlternateTitleDTO;
import cc.onelooker.kaleido.dto.AttributeDTO;
import cc.onelooker.kaleido.dto.PathInfoDTO;
import cc.onelooker.kaleido.dto.comic.ComicAuthorDTO;
import cc.onelooker.kaleido.dto.comic.ComicBookDTO;
import cc.onelooker.kaleido.dto.comic.ComicSeriesDTO;
import cc.onelooker.kaleido.enums.AttributeType;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.nfo.ComicInfoNFO;
import cc.onelooker.kaleido.nfo.NFOUtil;
import cc.onelooker.kaleido.service.comic.ComicAuthorService;
import cc.onelooker.kaleido.service.comic.ComicBookAuthorService;
import cc.onelooker.kaleido.service.comic.ComicBookService;
import cc.onelooker.kaleido.service.comic.ComicSeriesService;
import cc.onelooker.kaleido.third.komga.*;
import cc.onelooker.kaleido.third.tmm.Comic;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
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
import java.util.Objects;
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
    private ComicBookAuthorService comicBookAuthorService;

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
    public void sync(Book book) {
        syncBook(book);
        Series series = komgaApiService.findSeries(book.getSeriesId());
        syncSeries(series);
    }

    @Transactional
    public void syncSeries(String seriesId) {
        Series series = komgaApiService.findSeries(seriesId);
        syncSeries(series);
        List<Book> bookList = komgaApiService.listBookBySeries(seriesId);
        for (Book book : bookList) {
            syncBook(book);
        }
    }

    @Transactional
    public void matchPath(Path path, String bgmId) {
        Path downloadPath = Paths.get(ConfigUtils.getSysConfig(ConfigKey.comicDownloadPath));
        try {
            if (!Files.isDirectory(path)) {
                String baseName = FilenameUtils.getBaseName(path.getFileName().toString());
                path = downloadPath.resolve(baseName);
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
            if (comic == null) {
                log.info("== 忽略找不到匹配信息的文件:{}", path);
                return;
            }
            log.info("== 找到匹配信息:({}){}", comic.getBgmId(), comic.getSeries());
            Files.list(path).forEach(s -> convertBook(s, comic));
            if (FileUtils.isEmptyDirectory(path.toFile())) {
                NioFileUtils.deleteIfExists(path);
                log.info("== 删除源文件夹:{}", path.getFileName());
                pathInfoService.deleteByPath(path.toString());
                log.info("== 清除文件夹信息记录:{}", path);
            }
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
        String libraryPath = ConfigUtils.getSysConfig(ConfigKey.comicLibraryPath);
        //TODO
        Path path = Paths.get(libraryPath, StringUtils.removeStart(comicBookDTO.getPath(), "/comic"));
        Extractor extractor = CompressUtil.createExtractor(CharsetUtil.defaultCharset(), path.toFile());
        Path tempPath = Paths.get(System.getProperty("java.io.tmpdir"), "kaleido");
        extractor.extract(tempPath.toFile(), f -> StringUtils.equals(f.getName(), "ComicInfo.xml"));
        extractor.close();
        ComicInfoNFO comicInfoNFO = NFOUtil.read(ComicInfoNFO.class, tempPath, "ComicInfo.xml");
        Objects.requireNonNull(comicInfoNFO);
        ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(seriesId);
        comicSeriesDTO.setStatus(comicInfoNFO.getSeriesStatus());
        comicSeriesDTO.setBgmId(comicInfoNFO.getSeriesBgmId());
        comicSeriesDTO.setRating(Float.parseFloat(comicInfoNFO.getCommunityRating()));
        comicSeriesService.update(comicSeriesDTO);
        syncAlternateTitle(comicInfoNFO.getAkas(), seriesId);
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
            String libraryPath = ConfigUtils.getSysConfig(ConfigKey.comicLibraryPath);
            Path targetFolder = Paths.get(libraryPath, KaleidoUtils.genComicFolder(comicInfoNFO));
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

            List<Comic.Volume> volumes = comic.getVolumes();
            Integer number = getVolumeNumber(path.getFileName().toString());
            if (number != null) {
                comicInfoNFO.setTitle("卷" + number);
                comicInfoNFO.setNumber(String.valueOf(number));
            }
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
            NFOUtil.write(comicInfoNFO, ComicInfoNFO.class, folderPath, "ComicInfo.xml");
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

    private void syncBook(Book book) {
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
        comicBookDTO.setSummary(metadata.getSummary());
        comicBookDTO.setBookNumber(metadata.getNumber());
        comicBookDTO.setPageCount(media.getPagesCount());
        comicBookDTO.setPath(book.getUrl());
        comicBookDTO.setBgmId(getBgmId(metadata.getLinks()));
        comicBookDTO.setFileSize(book.getSizeBytes());
        comicBookDTO.setAddedAt(book.getAddedAt());
        comicBookDTO.setUpdatedAt(book.getUpdatedAt());
        if (isNew) {
            comicBookService.insert(comicBookDTO);
        } else {
            comicBookService.update(comicBookDTO);
        }
        syncAuthor(metadata.getAuthors(), comicBookDTO);
        syncAttribute(metadata.getTags(), comicBookDTO.getId(), AttributeType.ComicTag);
    }

    private void syncSeries(Series series) {
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
        comicSeriesDTO.setId(series.getId());
        comicSeriesDTO.setTitle(metadata.getTitle());
        comicSeriesDTO.setSummary(metadata.getSummary());
        comicSeriesDTO.setBookCount(metadata.getTotalBookCount());
        comicSeriesDTO.setBgmId(getBgmId(metadata.getLinks()));
        comicSeriesDTO.setPublisher(metadata.getPublisher());
        comicSeriesDTO.setPath(series.getUrl());
        comicSeriesDTO.setStatus(metadata.getStatus());
        comicSeriesDTO.setAddedAt(series.getAddedAt());
        comicSeriesDTO.setUpdatedAt(series.getUpdatedAt());
        if (isNew) {
            comicSeriesService.insert(comicSeriesDTO);
        } else {
            comicSeriesService.update(comicSeriesDTO);
        }
        syncAlternateTitle(metadata.getAlternateTitles().stream().map(AlternateTitle::getTitle).collect(Collectors.toList()), comicSeriesDTO.getId());
        syncAttribute(metadata.getTags(), comicSeriesDTO.getId(), AttributeType.ComicTag);
    }

    private void syncAuthor(List<Author> authors, ComicBookDTO comicBookDTO) {
        comicBookAuthorService.deleteByBookId(comicBookDTO.getId());
        if (authors == null) {
            return;
        }
        for (Author author : authors) {
            ComicAuthorDTO comicAuthorDTO = comicAuthorService.findByName(author.getName());
            if (comicAuthorDTO == null) {
                comicAuthorDTO = comicAuthorService.insert(author.getName());
            }
            comicBookAuthorService.insert(comicBookDTO.getId(), comicAuthorDTO.getId(), author.getRole());
        }
    }

    private void syncAlternateTitle(List<String> alternateTitles, String subjectId) {
        alternateTitleService.deleteBySubjectId(subjectId);
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
        subjectAttributeService.deleteBySubjectIdAndAttributeType(subjectId, type.name());
        for (String value : attributes) {
            AttributeDTO attributeDTO = attributeService.findByValueAndType(value, type.name());
            if (attributeDTO == null) {
                attributeDTO = attributeService.insert(value, type.name());
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
