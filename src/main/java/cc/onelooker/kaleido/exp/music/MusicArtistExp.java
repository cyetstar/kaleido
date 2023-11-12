package cc.onelooker.kaleido.exp.music;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 艺术家导出对象
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 *
 */
@Data
public class MusicArtistExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("MusicBrainzId")
    private String musicbrainzId;

    @ExcelProperty("Plex编号")
    private String plexId;

    @ExcelProperty("名称")
    private String mc;

    @Dict("ysjlx")
    @ExcelProperty("艺术家类型")
    private String ysjlx;

    @ExcelProperty("国家地区")
    private String gjdq;

    @ExcelProperty("简介")
    private String jj;

    @StringDateTimeFormat
    @ExcelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ExcelProperty("修改时间")
    private String xgsj;
}
