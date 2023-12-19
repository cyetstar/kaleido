package cc.onelooker.kaleido.exp.tvshow;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 单集演职员关联表导出对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 *
 */
@Data
public class TvshowEpisodeActorExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("单集id")
    private Long episodeId;

    @ExcelProperty("演职员id")
    private Long actorId;

    @ExcelProperty("角色")
    private String role;
}
