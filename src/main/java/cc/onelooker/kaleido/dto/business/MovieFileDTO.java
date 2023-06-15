package cc.onelooker.kaleido.dto.business;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

/**
 * 电影文件DTO
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 * @see cc.onelooker.kaleido.entity.business.MovieFileDO
 */
@Data
public class MovieFileDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -1170712909184907577L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 电影id
     */
    private Long movieId;

    /**
     * 文件名
     */
    private String wjm;

    /**
     * 文件路径
     */
    private String wjlj;

    /**
     * 文件大小
     */
    private Integer wjdx;

    private MovieFileVideoDTO movieFileVideoDTO;

    private List<MovieFileAudioDTO> movieFileAudioDTOList;

    private List<MovieFileSubtitleDTO> movieFileSubtitleDTOList;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
