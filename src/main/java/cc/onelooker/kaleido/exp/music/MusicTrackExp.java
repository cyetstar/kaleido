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
 * @date 2023-11-25 22:16:58
 *
 */
@Data
public class MusicTrackExp{

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

    @ExcelProperty("文件格式")
    private String format;

    @ExcelProperty("文件路径")
    private String path;

    @ExcelProperty("专辑id")
    private Long albumId;

    @ExcelProperty("曲长(毫秒)")
    private Integer duration;

    @ExcelProperty("曲号")
    private Integer trackIndex;

    @ExcelProperty("碟号")
    private Integer discIndex;

    @ExcelProperty("加入时间")
    private Long addedAt;

    @ExcelProperty("更新时间")
    private Long updatedAt;
}
