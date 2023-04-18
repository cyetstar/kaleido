package cc.onelooker.kaleido.dto.business.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import lombok.Data;

/**
 * 电影导出对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
public class MovieExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电影名称")
    private String dymc;

    @ExcelProperty("原片名")
    private String ypm;

    @ExcelProperty("用户评分")
    private Integer yhpf;

    @ExcelProperty("电影简介")
    private String dyjj;

    @ExcelProperty("电影标语")
    private String dyby;

    @ExcelProperty("影片时长")
    private Integer ypsc;

    @ExcelProperty("电影等级")
    private String dydj;

    @StringDateFormat
    @ExcelProperty("上映日期")
    private String syrq;

    @ExcelProperty("官网地址")
    private String gwdz;

    @Dict("gkbz")
    @ExcelProperty("是否观看")
    private String gkbz;

    @StringDateTimeFormat
    @ExcelProperty("观看时间")
    private String gksj;

    @Dict("scbz")
    @ExcelProperty("是否收藏")
    private String scbz;

    @StringDateTimeFormat
    @ExcelProperty("收藏时间")
    private String scsj;

    @ExcelProperty("plex编号")
    private String plexId;

    @StringDateTimeFormat
    @ExcelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ExcelProperty("修改时间")
    private String xgsj;
}
