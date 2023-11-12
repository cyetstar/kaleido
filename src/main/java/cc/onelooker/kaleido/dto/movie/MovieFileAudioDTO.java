package cc.onelooker.kaleido.dto.movie;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;

/**
 * 电影文件音频信息DTO
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 * @see cc.onelooker.kaleido.entity.movie.MovieFileAudioDO
 */
@Data
public class MovieFileAudioDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 2298609078472369510L;

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
     * 语言
     */
    private String language;

    /**
     * 声道
     */
    private String channels;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
