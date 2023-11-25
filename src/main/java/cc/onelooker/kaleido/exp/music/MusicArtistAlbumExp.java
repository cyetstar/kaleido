package cc.onelooker.kaleido.exp.music;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.Long;

/**
 * 艺术家专辑关联表导出对象
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 *
 */
@Data
public class MusicArtistAlbumExp{

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("艺术家id")
    private Long artistId;

    @ExcelProperty("专辑id")
    private Long albumId;
}
