package cc.onelooker.kaleido.entity.business;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 电影文件视频信息DO
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 * @see cc.onelooker.kaleido.dto.business.MovieFileVideoDTO
 */
@Data
@TableName("movie_file_video")
public class MovieFileVideoDO implements IdEntity<Long> {
    private static final long serialVersionUID = -3906976901574818826L;

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
     * 画幅比例
     */
    @TableField(value = "aspect")
    private String aspect;

    /**
     * 宽
     */
    @TableField(value = "width")
    private Integer width;

    /**
     * 高
     */
    @TableField(value = "height")
    private Integer height;

    /**
     * 时长
     */
    @TableField(value = "durationinseconds")
    private Integer durationinseconds;

    /**
     * 立体声模式
     */
    @TableField(value = "stereomode")
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
