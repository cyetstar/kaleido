package cc.onelooker.kaleido.exp.music;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 曲目导出对象
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 *
 */
@Data
public class MusicTrackExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("专辑Id")
    private Long albumId;

    @ExcelProperty("MusicBrainz编号")
    private String musicbrainzId;

    @ExcelProperty("Plex编号")
    private String plexId;

    @ExcelProperty("创建时间")
    private String cjsj;

    @ExcelProperty("修改时间")
    private String xgsj;

    @ExcelProperty("网易云音乐编号")
    private String neteaseId;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("艺术家")
    private String artists;

    @ExcelProperty("曲长")
    private Integer length;

    @ExcelProperty("曲号")
    private Integer trackNumber;

    @ExcelProperty("碟号")
    private Integer discNumber;

    @ExcelProperty("文件格式")
    private String format;

    @ExcelProperty("文件路径")
    private String path;
}
