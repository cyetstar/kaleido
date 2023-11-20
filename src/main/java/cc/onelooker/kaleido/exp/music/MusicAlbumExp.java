package cc.onelooker.kaleido.exp.music;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 专辑导出对象
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 *
 */
@Data
public class MusicAlbumExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("MusicBrainz编号")
    private String musicbrainzId;

    @ExcelProperty("Plex编号")
    private String plexId;

    @ExcelProperty("Plex缩略图")
    private String plexThumb;

    @ExcelProperty("网易云音乐编号")
    private String neteaseId;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("艺术家")
    private String artists;

    @ExcelProperty("简介")
    private String summary;

    @ExcelProperty("专辑类型")
    private String type;

    @ExcelProperty("音乐流派")
    private String genre;

    @ExcelProperty("发行国家")
    private String releaseCountry;

    @ExcelProperty("日期")
    private String date;

    @ExcelProperty("唱片公司")
    private String label;

    @ExcelProperty("发行日期")
    private String releaseDate;

    @ExcelProperty("碟数")
    private Integer totalDiscs;

    @ExcelProperty("音轨数")
    private Integer totalTracks;

    @ExcelProperty("媒体")
    private String media;

    @ExcelProperty("文件路径")
    private String path;

    @ExcelProperty("创建时间")
    private String cjsj;

    @ExcelProperty("修改时间")
    private String xgsj;
}
