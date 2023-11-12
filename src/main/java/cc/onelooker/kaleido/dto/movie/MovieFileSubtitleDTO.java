package cc.onelooker.kaleido.dto.movie;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * 电影文件字幕信息DTO
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 * @see cc.onelooker.kaleido.entity.movie.MovieFileSubtitleDO
 */
@Data
public class MovieFileSubtitleDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -7678253638897619010L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影文件id
     */
    private Long movieFileId;

    /**
     * 语言
     */
    private String language;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
