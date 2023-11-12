package cc.onelooker.kaleido.exp.music;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 曲目导出对象
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 *
 */
@Data
public class MusicTrackExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("发行品id")
    private Long releaseId;

    @ExcelProperty("MusicBrainzId")
    private String musicbrainzId;

    @ExcelProperty("Plex编号")
    private String plexId;

    @ExcelProperty("标题")
    private String bt;

    @ExcelProperty("艺术家")
    private String ysj;

    @ExcelProperty("长度")
    private String cd;

    @ExcelProperty("曲号")
    private Integer qh;

    @ExcelProperty("碟号")
    private Integer dh;

    @Dict("wjgs")
    @ExcelProperty("文件格式")
    private String wjgs;

    @ExcelProperty("文件路径")
    private String wjlj;

    @Dict("sfygc")
    @ExcelProperty("是否有歌词")
    private String sfygc;

    @Dict("sfqs")
    @ExcelProperty("是否缺损")
    private String sfqs;

    @StringDateTimeFormat
    @ExcelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ExcelProperty("修改时间")
    private String xgsj;
}
