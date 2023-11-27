package cc.onelooker.kaleido.exp.tvshow;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 剧集演职员关联表导出对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 *
 */
@Data
public class TvshowShowActorExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("剧集id")
    private Long showId;

    @ExcelProperty("演职员id")
    private Long actorId;

    @ExcelProperty("角色")
    private String role;
}
