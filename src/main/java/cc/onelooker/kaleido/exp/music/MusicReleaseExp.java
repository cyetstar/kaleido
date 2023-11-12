package cc.onelooker.kaleido.exp.music;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 发行品导出对象
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 *
 */
@Data
public class MusicReleaseExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("MusicBrainzId")
    private String musicbrainzId;

    @ExcelProperty("Plex编号")
    private String plexId;

    @ExcelProperty("标题")
    private String bt;

    @ExcelProperty("艺术家")
    private String ysj;

    @ExcelProperty("专辑类型")
    private String zjlx;

    @ExcelProperty("音乐流派")
    private String yylp;

    @ExcelProperty("发行国家")
    private String fxgj;

    @StringDateFormat
    @ExcelProperty("日期")
    private String rq;

    @ExcelProperty("唱片公司")
    private String cpgs;

    @StringDateFormat
    @ExcelProperty("首发日期")
    private String sfrq;

    @ExcelProperty("碟数")
    private Integer zds;

    @ExcelProperty("音轨数")
    private Integer ygs;

    @Dict("mt")
    @ExcelProperty("媒体")
    private String mt;

    @Dict("sfqs")
    @ExcelProperty("是否缺损")
    private String sfqs;

    @ExcelProperty("文件路径")
    private String wjlj;

    @StringDateTimeFormat
    @ExcelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ExcelProperty("修改时间")
    private String xgsj;
}
