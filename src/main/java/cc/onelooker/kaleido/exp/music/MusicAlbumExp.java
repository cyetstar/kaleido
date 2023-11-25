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
 * @date 2023-11-25 22:16:58
 *
 */
@Data
public class MusicAlbumExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("MusicBrainz编号")
    private String musicbrainzId;

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

    @ExcelProperty("唱片公司")
    private String label;

    @ExcelProperty("碟数")
    private Integer totalDiscs;

    @ExcelProperty("音轨数")
    private Integer totalTracks;

    @ExcelProperty("媒体")
    private String media;

    @ExcelProperty("首发年份")
    private String year;

    @ExcelProperty("首发日期")
    private String originallyAvailableAt;

    @ExcelProperty("封面图")
    private String thumb;

    @ExcelProperty("加入时间")
    private Long addedAt;

    @ExcelProperty("更新时间")
    private Long updatedAt;
}
