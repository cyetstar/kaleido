package cc.onelooker.kaleido.exp.music;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;

/**
 * 艺术家曲目关联表导出对象
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 *
 */
@Data
public class MusicArtistTrackExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("艺术家id")
    private Long artisId;

    @ExcelProperty("曲目id")
    private Long trackId;
}
