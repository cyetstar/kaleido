package cc.onelooker.kaleido.exp.music;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;

/**
 * 艺术家发行品关联表导出对象
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 *
 */
@Data
public class MusicArtistReleaseExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("艺术家id")
    private Long artistId;

    @ExcelProperty("发行品id")
    private Long releaseId;
}
