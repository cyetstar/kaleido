package cc.onelooker.kaleido.dto.movie;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 电影文件视频信息DTO
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 * @see cc.onelooker.kaleido.entity.movie.MovieFileVideoDO
 */
@Data
public class MovieFileVideoDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 6046538235710808783L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影文件id
     */
    private Long movieFileId;

    /**
     * 编解码器
     */
    private String codec;

    /**
     * 画幅比例
     */
    private String aspect;

    /**
     * 宽
     */
    private Integer width;

    /**
     * 高
     */
    private Integer height;

    /**
     * 时长
     */
    private Integer durationinseconds;

    /**
     * 立体声模式
     */
    private String stereomode;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
