package cc.onelooker.kaleido.exp.music;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 艺术家导出对象
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 *
 */
@Data
public class MusicArtistExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("MusicBrainz编号")
    private String musicbrainzId;

    @ExcelProperty("Plex编号")
    private String plexId;

    @ExcelProperty("创建时间")
    private String cjsj;

    @ExcelProperty("修改时间")
    private String xgsj;

    @ExcelProperty("Plex缩略图")
    private String plexThumb;

    @ExcelProperty("网易云音乐编号")
    private String neteaseId;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("国家地区")
    private String area;

    @ExcelProperty("简介")
    private String summary;
}
