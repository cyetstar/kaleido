package cc.onelooker.kaleido.exp.music;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 艺术家导出对象
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 *
 */
@Data
public class MusicArtistExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("MusicBrainz编号")
    private String musicbrainzId;

    @ExcelProperty("网易云音乐编号")
    private String neteaseId;

    @ExcelProperty("国家地区")
    private String area;

    @ExcelProperty("简介")
    private String summary;

    @ExcelProperty("名称")
    private String title;

    @ExcelProperty("排序名称")
    private String titleSort;

    @ExcelProperty("封面图")
    private String thumb;

    @ExcelProperty("加入时间")
    private Long addedAt;

    @ExcelProperty("更新时间")
    private Long updatedAt;
}
