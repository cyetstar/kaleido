package cc.onelooker.kaleido.entity.business;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.lang.String;

/**
 * 电影文件音频信息DO
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 * @see cc.onelooker.kaleido.dto.business.MovieFileAudioDTO
 */
@Data
@TableName("movie_file_audio")
public class MovieFileAudioDO implements IdEntity<Long> {
    private static final long serialVersionUID = -7870466620658786951L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 电影文件id
     */
    @TableField(value = "movie_file_id")
    private Long movieFileId;

    /**
     * 编解码器
     */
    @TableField(value = "codec")
    private String codec;

    /**
     * 语言
     */
    @TableField(value = "language")
    private String language;

    /**
     * 声道
     */
    @TableField(value = "channels")
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
